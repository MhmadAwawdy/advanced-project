package librarysystem.utils;

import librarysystem.models.Librarian;

public class SessionManager
{
    private static Librarian loggedInLibrarian;

    public static Librarian getLoggedInLibrarian()
    {
        return loggedInLibrarian;
    }
    public static void setLoggedInLibrarian(Librarian librarian)
    {
        loggedInLibrarian = librarian;
    }
}
