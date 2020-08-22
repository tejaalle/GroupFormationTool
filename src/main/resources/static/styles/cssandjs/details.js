    function showDetails(toggleButtonID) {
        var questionDetails = document.getElementById('Q' + toggleButtonID);
        var toggleButton = document.getElementById(toggleButtonID);
        if (questionDetails.style.display === "none") {
            questionDetails.style.display = "block";
            toggleButton.innerHTML = "Hide Question Details";
        } else {
            questionDetails.style.display = "none";
            toggleButton.innerHTML = "Show Question Details";
        }
    }