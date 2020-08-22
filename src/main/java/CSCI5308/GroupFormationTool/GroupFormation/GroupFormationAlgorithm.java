package CSCI5308.GroupFormationTool.GroupFormation;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupFormationAlgorithm implements IAlgorithm {
    private static final String SIMILAR = "SIMILAR";

    public List<IQuestion> setCriteria(List<IQuestion> questions, List<IQuestion> criteriaQuestions) {

        for (IQuestion question : questions) {
            for (IQuestion criteriaQuestion : criteriaQuestions) {
                if (question.getID() == criteriaQuestion.getID()) {
                    question.setCriteria(criteriaQuestion.getCriteria());
                    question.setType(criteriaQuestion.getType());
                }
            }
        }
        return questions;
    }

    public int getMax(List<Integer> ranks) {
        List<Integer> rankList = new ArrayList<>();
        for (int i : ranks) {
            rankList.add(i);
        }
        Collections.sort(rankList);
        return rankList.get(rankList.size() - 1);
    }

    public int getMaximum(int[][] ranks) {
        List<Integer> rankList = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            for (int j = i; j < ranks[i].length; j++) {
                rankList.add(ranks[i][j]);
            }
        }
        Collections.sort(rankList);
        return rankList.get(rankList.size() - 1);
    }

    public void removeNumber(List<Integer> rankList, int[][] ranks) {
        for (int i = 0; i < ranks.length; i++) {
            for (int number : rankList) {
                ranks[i][number] = -1;
                ranks[number][i] = -1;
            }
        }
    }

    public List<Integer> getFirstTwoNumbers(int[][] ranks, int max) {
        List<Integer> group = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            for (int j = i; j < ranks.length; j++) {
                if (ranks[i][j] == max) {
                    group.add(i);
                    group.add(j);
                    return group;
                }
            }
        }
        return null;
    }


    @Override
    public List<List<Long>> createGroups(List<Long> studentIdList, List<List<IQuestion>> questionsList,
                                         List<IQuestion> criteriaQuestions, int groupCount) {
        int[][] ranks = new int[studentIdList.size()][studentIdList.size()];
        for (List<IQuestion> questions : questionsList) {
            questions = setCriteria(questions, criteriaQuestions);
        }

        for (int i = 0; i < questionsList.size(); i++) {
            ranks[i][i] = -1;
        }
        System.out.println(ranks);
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < ranks[i].length; j++) {
                System.out.print(ranks[i][j] + "\t");
            }
            System.out.println();
        }
        for (int i = 0; i < questionsList.size(); i++) {
            for (int j = i + 1; j < questionsList.size(); j++) {
                List<IQuestion> questionCompare1 = questionsList.get(i);
                List<IQuestion> questionCompare2 = questionsList.get(j);
                int weight = 0;
                for (int k = 0; k < questionCompare1.size(); k++) {
                    if (questionCompare1.get(k).getCriteria().equals(SIMILAR)) {
                        if (questionCompare1.get(k).getAnswer().equals(questionCompare2.get(k).getAnswer())) {
                            weight = weight + 1;
                        }
                    } else {
                        boolean isNotEquals = !(questionCompare1.get(k).getAnswer().equals(questionCompare2.get(k).getAnswer()));
                        if (isNotEquals) {
                            weight = weight + 1;
                        }
                    }
                }
                ranks[i][j] = weight;
                ranks[j][i] = weight;
            }
        }
        System.out.println(ranks);
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < ranks[i].length; j++) {
                System.out.print(ranks[i][j] + "\t");
            }
            System.out.println();
        }
        List<List<Integer>> totalGroups = new ArrayList<>();

        while (totalGroups.size() < (int) Math.ceil((float) studentIdList.size() / groupCount)) {
            System.out.println("entered");
            List<Integer> group = new ArrayList<>();
            System.out.println(totalGroups);
            for (int i = 0; i < ranks.length; i++) {
                for (int j = 0; j < ranks[i].length; j++) {
                    System.out.print(ranks[i][j] + "\t");
                }
                System.out.println();
            }
            List<Integer> firstTwoNumbers = getFirstTwoNumbers(ranks, getMaximum(ranks));
            System.out.println(firstTwoNumbers);
            int firstNumber = firstTwoNumbers.get(0);
            int secondNumber = firstTwoNumbers.get(1);
            if (firstNumber == secondNumber) {
                break;
            }
            ranks[firstNumber][secondNumber] = -1;
            ranks[secondNumber][firstNumber] = -1;
            group.add(firstNumber);
            group.add(secondNumber);
            List<Integer> averageArray = new ArrayList<>();
            for (int i = 0; i < ranks[firstNumber].length; i++) {
                averageArray.add((ranks[firstNumber][i] + ranks[secondNumber][i]) / 2);
            }
            System.out.println("averageArray");
            System.out.println(averageArray);
            int countOfMinusOne = Collections.frequency(averageArray, -1);
            if (countOfMinusOne == studentIdList.size() - 1) {
                groupCount = groupCount + 1;
            }
            for (int i = 0; i < groupCount - 2; i++) {
                int maxAmongAverage = getMax(averageArray);
                if (maxAmongAverage == -1) {
                    break;
                }
                int nextIndex = averageArray.indexOf(maxAmongAverage);
                group.add(nextIndex);
                averageArray.set(nextIndex, -1);
                countOfMinusOne = Collections.frequency(averageArray, -1);
                if (countOfMinusOne == studentIdList.size() - 1) {
                    groupCount = groupCount + 1;
                }
            }
            removeNumber(group, ranks);
            totalGroups.add(group);

        }
        System.out.println(totalGroups);
        List<List<Long>> actualGroups = new ArrayList<>();
        for (List<Integer> group : totalGroups) {
            List<Long> actualGroup = new ArrayList<>();
            for (int member : group) {
                actualGroup.add(studentIdList.get(member));
            }
            actualGroups.add(actualGroup);
        }
        System.out.println(actualGroups);

        return actualGroups;
    }

}
