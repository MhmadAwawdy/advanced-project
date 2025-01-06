package librarysystem.utils;

public class ValidationUtils
{
    public static boolean isValidID(String studentID)
    {
        if (studentID == null)
        {
            return false;
        }
        return studentID.matches("\\d{8}");
    }
    public static boolean isValidPhoneNumber(String phoneNumber)
    {
        if (phoneNumber == null)
        {
            return false;
        }
        return phoneNumber.matches("05\\d{8}");
    }
}
