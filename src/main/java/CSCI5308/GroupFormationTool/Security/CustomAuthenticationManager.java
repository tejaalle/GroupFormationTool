package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.AccessControl.AccessControlAbstractFactory;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.ErrorHandling.LoggerUtil;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    private static final String ADMIN_BANNER_ID = "B-000000";
    Logger myLogger = LoggerUtil.getLoggerInstance(CustomAuthenticationManager.class);

    private Authentication checkAdmin(String password, User user, Authentication authentication) throws AuthenticationException {
        // The admin password is not encrypted because it is hardcoded in the DB.
        if (password.equals(user.getPassword())) {
            // Grant ADMIN rights system-wide, this is used to protect controller mappings.
            List<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
            rights.add(new SimpleGrantedAuthority("ADMIN"));

            // Return valid authentication token.
            UsernamePasswordAuthenticationToken token;
            token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), rights);
            myLogger.info(String.format("User with bannerID %s logged in successfully", user.getBannerID()));

            return token;
        } else {
            myLogger.info(String.format("User with bannerID %s failed to login because of bad credentials", user.getBannerID()));
            throw new BadCredentialsException("1000");
        }
    }

    private Authentication checkNormal(String password, User u, Authentication authentication) throws AuthenticationException {
        SecurityAbstractFactory abstractFactory = SystemConfig.instance().getSecurityConcreteFactoryState();
        IPasswordEncryption passwordEncryption = abstractFactory.makeBCryptPasswordEncryption();
        if (passwordEncryption.matches(password, u.getPassword())) {
            // Grant USER rights system-wide, this is used to protect controller mappings.
            List<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
            rights.add(new SimpleGrantedAuthority("USER"));

            // Return valid authentication token.
            UsernamePasswordAuthenticationToken token;
            token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), rights);
            myLogger.info(String.format("User with bannerID %s logged in successfully", u.getBannerID()));

            return token;
        } else {
            myLogger.info(String.format("User with bannerID %s failed to login because of bad credentials", u.getBannerID()));
            throw new BadCredentialsException("1000");
        }
    }

    // Authenticate against our database using the input banner ID and password.
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessControlAbstractFactory accessControlAbstractFactory = SystemConfig.instance().getAccessControlConcreteFactoryState();
        String bannerID = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        IUserPersistence userDB = accessControlAbstractFactory.makeUserDB();
        User user;

        try {
            user = new User(bannerID, userDB);
        } catch (Exception e) {
            myLogger.info(String.format("There was an authentication Exception while loading"
                    + " details for user with bannerID %s", bannerID));
            throw new AuthenticationServiceException("1000");
        }
        if (user.isValidUser()) {
            if (bannerID.toUpperCase().equals(ADMIN_BANNER_ID)) {
                return checkAdmin(password, user, authentication);
            } else {
                return checkNormal(password, user, authentication);
            }
        } else {
            myLogger.info(String.format("User with bannerID %s failed to login because of bad credentials", bannerID));
            throw new BadCredentialsException("1001");
        }
    }

}
