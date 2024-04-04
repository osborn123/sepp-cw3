package controller;

public class ModifiableEntity {
    private boolean modified;

    // Method to determine if the entity has been modified.
    public boolean isModified() {
        return modified;
    }

    // Method to mark the entity as modified.
    public void markAsModified() {
        this.modified = true;
    }

    // Method to reset the modified status after saving changes.
    public void resetModified() {
        this.modified = false;
    }
}