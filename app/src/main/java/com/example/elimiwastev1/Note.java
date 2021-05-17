package com.example.elimiwastev1;


import java.util.ArrayList;
import java.util.Date;

/**
 * This class models the behavior of a Note object, which stores information from user-entry and provides methods
 * to access data stored in SQLite database
 */
//Tutorial followed to create class: https://www.youtube.com/watch?v=4k1ZMpO9Zn0&t=884s
public class Note {

        public static ArrayList<Note> noteArrayList = new ArrayList<>(); //ArrayList of Note object
        public static String NOTE_EDIT_EXTRA =  "noteEdit"; //String noteEdit

        private int id; //Stores id of Note object
        private String title; //Stores title or food item name of Note object
        private String description; //Stores description, or purchase date of Note object
        private String expiration; //Stores expiration date of Note object
        private Date deleted; //Stores the date a Note item is deleted from NoteAdapter


    /**
     * Parameter constructor
     * Takes the following 5 inputs to construct an object of the note class:
     * @param id stores a int value of a user id, starting at 0
     * @param title stores the food item name as String
     * @param description stores purchase date as String
     * @param expiration stores expiration date as String
     * @param deleted stores date Note object is deleted as Date
     */
    public Note(int id, String title, String description, String expiration, Date deleted) //added String expiration
        {
            this.id = id;
            this.title = title;
            this.description = description;
            this.expiration = expiration;
            this.deleted = deleted;

        }

    /**
     * Parameter constructor
     * Takes the following 4 inputs to construct an object of the note class:
     * @param id stores a int value of a user id, starting at 0
     * @param title stores the food item name as String
     * @param description stores purchase date as String
     * @param expiration stores expiration date as String
     */
    public Note(int id, String title, String description, String expiration)
        {
            this.id = id;
            this.title = title;
            this.description = description;
            this.expiration = expiration; // added expiration
            deleted = null;
        }

    /**
     * Getter method
     * Takes input of item ID and returns corresponding object of Note in noteArrayList or null
     * @param passedNoteID is the user inputted ID
     * @return object note in Note class with corresponding user input ID
     */
    public static Note getNoteForID(int passedNoteID)
        {
            for (Note note : noteArrayList)
            {
                if(note.getId() == passedNoteID)
                    return note;
            }

            return null;
        }


    /**
     * Stores Note objects in ArrayList<Note> that are not deleted by the user
     * @return ArrayList of Note class if it has a null return when the getDeleted method is called
     */
    public static ArrayList<Note> nonDeletedNotes() {
            ArrayList<Note> nonDeleted = new ArrayList<>();
            for(Note note : noteArrayList)
            {
                if(note.getDeleted() == null)
                    nonDeleted.add(note);
            }

            return nonDeleted;
        }

    /**
     * Getter method to return id of Note object
     * @return corresponding ID of note object
     */
    public int getId()
        {

            return id;
        }

    /**
     * Setter method
     * Takes user input ID and sets it the the ID of the Note object
     * @param id user-defined ID stored as an int
     */
    public void setId(int id)
        {
            this.id = id;
        }

    /**
     * Getter method
     * Gets title or food item name of object in Note class
     * @return the title or food item name in Note class
     */
    public String getTitle()
        {

            return title;
        }

    /**
     * Setter method
     * Sets the title or food item name of object in Note class to user-defined String
     * @param title is the user-defined String which will become the ittle of the corresponding object in the Note class
     */
    public void setTitle(String title)
        {

            this.title = title;
        }

    /**
     * Getter method
     * Retrieves the description or purchase date of Note object
     * @return the description as a String
     */
    public String getDescription()
        {

            return description;
        }

    /**
     * Setter method
     * Sets the description or purchase date of Note object as user-defined String input
     * @param description the user-defined description or purchase date stored as String
     */
    public void setDescription(String description)
        {
            this.description = description;
        }


    /**
     * Getter method
     * Gets the expiration date of Note object
     * @return the expiration date of the Note object as String
     */
    public String getExpiration() {

            return expiration;
        }

    /**
     * Setter method
     * Sets the expiration date of the Note object to user-defined String
     * @param expiration the expiration date of the Note object, stored as a String
     */
        public void setExpiration(String expiration) {

             this.expiration = expiration;

        }

    /**
     * Getter method
     * Gets the date a Note object was deleted from the ListView
     * @return the deleted date as Date
     */
    public Date getDeleted()
        {

            return deleted;
        }

    /**
     * Setter method
     * Sets the date a Note object was deleted from the ListView as user-defined Date
     * @param deleted the deleted date of a note object
     */
    public void setDeleted(Date deleted)
        {
            this.deleted = deleted;
        }
    }

