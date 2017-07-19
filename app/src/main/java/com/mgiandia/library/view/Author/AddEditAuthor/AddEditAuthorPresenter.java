package com.mgiandia.library.view.Author.AddEditAuthor;

import com.mgiandia.library.dao.AuthorDAO;
import com.mgiandia.library.domain.Author;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddEditAuthorPresenter {

    private AddEditAuthorView view;
    private AuthorDAO authors;
    Author attachedAuthor;

    /**
     * Αρχικοποιεί τον Presenter έτσι ώστε
     * αργότερα να προστεθεί ή να τροποποιηθεί
     * ο συγγραφέας.
     * @param view Ένα instance του view
     * @param authors Ένα instance του author
     */
    public AddEditAuthorPresenter(AddEditAuthorView view, AuthorDAO authors) {
        this.view = view;
        this.authors = authors;

        Integer attachedAuthorID = view.getAttachedAuthorID();
        attachedAuthor = attachedAuthorID == null ? null : authors.find(attachedAuthorID);

        if(attachedAuthor != null)//edit mode
        {
            view.setPageName("Συγγραφέας #" + attachedAuthor.getId());
            view.setFirstName(attachedAuthor.getFirstName());
            view.setLastName(attachedAuthor.getLastName());
        }
    }

    /**
     * Κατά την αποθήκευση του συγγραφέα ελέγχει
     * αν το όνομα και το επώνυμο είναι πάνω από
     * 2 χαρακτήρες και κάτω από 15. Αν είναι
     * εμφανίζει μήνυμα ότι ήταν επιτυχείς η
     * εγγραφή ή η τροποποίηση.
     */
    public void onSaveAuthor()
    {
        String
                firstName = view.getFirstName(),
                lastName = view.getLastName();

        if(firstName.length() < 2 || firstName.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στο όνομα.");
        else if(lastName.length() < 2 || lastName.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στο επώνυμο.");
        else
        {
            if(attachedAuthor == null)//add
            {
                authors.save(new Author(authors.nextId(), firstName, lastName));

                view.successfullyFinishActivity("Επιτυχής Εγγραφή του '"+lastName+" "+firstName+"'!");
            }
            else//update
            {
                attachedAuthor.setFirstName(firstName);
                attachedAuthor.setLastName(lastName);

                view.successfullyFinishActivity("Επιτυχής Τροποποίηση του '"+lastName+" "+firstName+"'!");
            }
        }
    }
}
