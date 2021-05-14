package com.example.elimiwastev1;


import java.util.ArrayList;
import java.util.Date;


//Update
public class Note {

        public static ArrayList<Note> noteArrayList = new ArrayList<>();
        public static String NOTE_EDIT_EXTRA =  "noteEdit";

        private int id;
        private String title;
        private String description;
        private String expiration; //added
        private Date deleted;


    public Note(int id, String title, String description, String expiration, Date deleted) //added String expiration
        {
            this.id = id;
            this.title = title;
            this.description = description;
            this.expiration = expiration; //added
            this.deleted = deleted;

        }

    public Note(int id, String title, String description, String expiration)
        {
            this.id = id;
            this.title = title;
            this.description = description;
            this.expiration = expiration; // added expiration
            deleted = null;
        }

        public static Note getNoteForID(int passedNoteID)
        {
            for (Note note : noteArrayList)
            {
                if(note.getId() == passedNoteID)
                    return note;
            }

            return null;
        }


        public static ArrayList<Note> nonDeletedNotes()
        {
            ArrayList<Note> nonDeleted = new ArrayList<>();
            for(Note note : noteArrayList)
            {
                if(note.getDeleted() == null)
                    nonDeleted.add(note);
            }

            return nonDeleted;
        }

        public int getId()
        {

            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getTitle()
        {

            return title;
        }

        public void setTitle(String title)
        {

            this.title = title;
        }

        public String getDescription()
        {

            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        //added
        public String getExpiration() {

            return expiration;
        }

        //added
        public void setExpiration(String expiration) {

             this.expiration = expiration;

        }

        public Date getDeleted()
        {

            return deleted;
        }

        public void setDeleted(Date deleted)
        {
            this.deleted = deleted;
        }
    }

