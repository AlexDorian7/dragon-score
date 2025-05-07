package team.logica_populi.dragonscore.base.points;

import java.util.Random;
import java.time.LocalDate;

/**
 *  Generates a submission code for completion of lesson exercises
 */
public class SubmissionCode {
    private final String code;

    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Generates a submission code through an encryption calculations.
     * @param name name of the user
     * @param lesson the lesson id
     * @param first index of last character of user's first name in the alphabet
     * @param last index last character of user's last name in the alphabet
     * @param les index of lesson character in the alphabet
     * @return The generated the submission code
     */
    private static String generateCode(String name, String lesson, char lessonLetter, int first, int last, int les) {
        Random random = new Random();
        LocalDate date = LocalDate.now();

        String head = String.valueOf(random.nextInt(1000));
        String tail = String.valueOf(random.nextInt(1000));
        String data = String.valueOf((first * les) + (last * les));

        return head + "." + data + "." + tail + "(" + name + "-" + lesson + "-" + lessonLetter +" " + date +")";
    }

    /**
     * Default constructor
     * Generates a submission code which students will turn in for their assignment
     * @param fName first name of the user
     * @param lName last name of the user
     * @param lessonID the current lesson the user is working on
     * @param lessonName the full name of the lesson
     */
    public SubmissionCode(String fName, String lName, String lessonID, String lessonName) {
        String fullName = fName + " " + lName;

        char firstCh = fName.charAt(fName.length() - 1);
        char lastCh = lName.charAt(lName.length() - 1);
        char lessonCh = lessonName.toLowerCase().charAt(lessonName.length() - 1);


        int firstNameIndex = firstCh - 96; // a = 97, 0x61
        int lastNameIndex = lastCh - 96;
        int lessonIdIndex = lessonCh - 96; // Should note that lesson ids could have an upper case letter. In that case this number will be negative

//        for (int i = 0; i < ALPHABET.length; i++) {
//            if (ALPHABET[i] == firstCh) {
//                firstNameIndex = i + 1;
//            } else if (ALPHABET[i] == lastCh) {
//                lastNameIndex = i + 1;
//            } else if (ALPHABET[i] == lessonCh) {
//                lessonIdIndex = i + 1;
//            }
//        }

        this.code = generateCode(fullName, lessonID, lessonCh ,firstNameIndex, lastNameIndex, lessonIdIndex);
    }

    /**
     * Gets the submission code
     * @return the generated submission code
     */
    public String getCode() {
        return code;
    }
}