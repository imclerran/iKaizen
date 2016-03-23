package mclerrani.ikaizen;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Data class for storing Kaizen information
 *
 * @author Ian McLerran
 * @version 3/12/16
 */
public class Kaizen implements Comparable<Kaizen> {

    // meta data:
    private int itemID;
    private DateTime dateModified;
    private boolean deleteMe = false;

    // primary data:
    // TODO: add material waste and labor waste
    private String owner;
    private String dept;
    private DateTime dateCreated;
    private String problemStatement;
    private String overProductionWaste;
    private String transportationWaste;
    private String motionWaste;
    private String waitingWaste;
    private String processingWaste;
    private String inventoryWaste;
    private String defectsWaste;
    private String rootCauses;
    private int totalWaste;
    private ArrayList<ImageFile> imageFiles;
    private Solution solution;

    /**
     * default constructor
     */
    public Kaizen() {
        dateCreated = DateTime.now();
        dateModified = DateTime.now();
        totalWaste = 0;

        imageFiles = new ArrayList<>();

        //solution = new Solution();
        solution = null;
    }

    /**
     * constructor
     *
     * @param owner -- the owner of the Kaizen
     * @param dept -- the department the owner works in
     * @param dateCreated -- the date the Kaizen was created
     * @param dateModified -- the date the Kaizen was last modified
     * @param problemStatement -- the problem to solve
     * @param overProductionWaste -- wastes caused by the problem
     * @param transportationWaste -- wastes caused by the problem
     * @param motionWaste -- wastes caused by the problem
     * @param waitingWaste -- wastes caused by the problem
     * @param processingWaste -- wastes caused by the problem
     * @param inventoryWaste -- wastes caused by the problem
     * @param defectsWaste -- wastes caused by the problem
     * @param rootCauses -- causes of the problem
     * @param totalWaste -- total time wasted
     */
    public Kaizen(String owner, String dept, DateTime dateCreated, DateTime dateModified, String problemStatement,
                  String overProductionWaste, String transportationWaste, String motionWaste, String waitingWaste,
                  String processingWaste, String inventoryWaste, String defectsWaste, String rootCauses, int totalWaste)
    {
        this.owner = owner;
        this.dept = dept;
        this.dateCreated = dateCreated;

        if(null != dateModified)
            this.dateModified = dateModified;
        else
            this.dateModified = DateTime.now();

        this.problemStatement = problemStatement;
        this.overProductionWaste = overProductionWaste;
        this.transportationWaste = transportationWaste;
        this.motionWaste = motionWaste;
        this.waitingWaste = waitingWaste;
        this.processingWaste = processingWaste;
        this.inventoryWaste = inventoryWaste;
        this.defectsWaste = defectsWaste;
        this.rootCauses = rootCauses;
        this.totalWaste = totalWaste;

        if(null != imageFiles)
            this.imageFiles = imageFiles;
        else
            this.imageFiles = new ArrayList<>();

        solution = null;
    }

    // getters and setters:

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public DateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(DateTime dateCreated) { this.dateCreated = dateCreated; }

    public DateTime getDateModified() { return dateModified; }
    public void setDateModified(DateTime dateModified) { this.dateModified = dateModified; }


    public String getProblemStatement() { return problemStatement; }
    public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }

    public String getOverProductionWaste() { return overProductionWaste; }
    public void setOverProductionWaste(String overProductionWaste) { this.overProductionWaste = overProductionWaste; }

    public String getTransportationWaste() { return transportationWaste; }
    public void setTransportationWaste(String transportationWaste) { this.transportationWaste = transportationWaste; }

    public String getWaitingWaste() { return waitingWaste; }
    public void setWaitingWaste(String waitingWaste) { this.waitingWaste = waitingWaste; }

    public String getProcessingWaste() { return processingWaste; }
    public void setProcessingWaste(String processingWaste) { this.processingWaste = processingWaste; }

    public String getInventoryWaste() { return inventoryWaste; }
    public void setInventoryWaste(String inventoryWaste) { this.inventoryWaste = inventoryWaste; }

    public String getDefectsWaste() { return defectsWaste; }
    public void setDefectsWaste(String defectsWaste) { this.defectsWaste = defectsWaste; }

    public String getRootCauses() { return rootCauses; }
    public void setRootCauses(String rootCauses) { this.rootCauses = rootCauses; }

    public int getTotalWaste() { return totalWaste; }
    public void setTotalWaste(int totalWaste) { this.totalWaste = totalWaste; }

    public String getMotionWaste() { return motionWaste; }
    public void setMotionWaste(String motionWaste) { this.motionWaste = motionWaste; }

    public ArrayList<ImageFile> getImageFiles() { return imageFiles; }
    public void setImageFiles(ArrayList<ImageFile> imageFiles) { this.imageFiles = imageFiles; }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public boolean isDeleteMe() { return deleteMe; }
    public void setDeleteMe(boolean deleteMe) { this.deleteMe = deleteMe; }

    public Solution getSolution() { return solution; }
    public void setSolution(Solution solution) { this.solution = solution; }

    /**
     * add an ImageFile to the list
     *
     * @param f -- the ImageFile to add
     * @return the added ImageFile
     */
    public ImageFile addImageFile(ImageFile f) {
        imageFiles.add(f);
        return f;
    }

    /**
     * remove an ImageFile from the list
     *
     * @param f -- the ImageFile to remove
     * @return the removed ImageFile
     */
    public ImageFile removeImageFile(int f) { return imageFiles.remove(f); }

    /**
     * get a string representation of the date created
     *
     * @return the date created formatted as a string
     */
    public String getDateCreatedAsString() {
        return dateCreated.toString("MM/dd/yyyy");
    }


    /**
     * set the date modified to the current time
     */
    public void updateDateModified() {
        dateModified = DateTime.now();
    }

    /**
     * create a new Kaizen object using example values
     *
     * @return the new Kaizen
     */
    public static Kaizen getTestKaizen() {
        DateTime today = DateTime.now();

        Kaizen test = new Kaizen("Your Name", "N/A", today, null,
                "Make sure your problem statement is not a root cause!",
                "We made too much!", "She had to carry it too far!",
                "I had to put it away and get it out again",
                "We had to wait for them to finish!",
                "They don't need us to do this!", "He ran out of parts!", "I damaged the product!",
                "1) this definitely caused the problem\n2) this might have contributed to the issue\n3) This could be part of the problem", 365);
        return test;
    }

    /**
     * Serialize the Kaizen object as a json representation
     *
     * @return the string containing the serialized object
     */
    public String toJson() { return new Gson().toJson(this); }

    /**
     * create a new Kaizen object from a json string
     *
     * @param jsonString the json to deserialize
     * @return the new Kaizen object
     */
    public static Kaizen fromJson(String jsonString) {
        return new Gson().fromJson(jsonString, Kaizen.class);
    }

    /**
     * compare two Kaizen objects
     *
     * @param another the second Kaizen to compare this Kaizen to
     * @return an int indicating their sort order
     */
    @Override
    public int compareTo(Kaizen another) {
        return another.dateModified.compareTo(this.dateModified);
    }

    /**
     * check if one Kaizen equals another
     *
     * @param o the second Kaizen to compare this one to
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kaizen kaizen = (Kaizen) o;

        if (itemID != kaizen.itemID) return false;
        if (totalWaste != kaizen.totalWaste) return false;
        if (deleteMe != kaizen.deleteMe) return false;
        if (owner != null ? !owner.equals(kaizen.owner) : kaizen.owner != null) return false;
        if (dept != null ? !dept.equals(kaizen.dept) : kaizen.dept != null) return false;
        if (dateCreated != null ? !dateCreated.equals(kaizen.dateCreated) : kaizen.dateCreated != null)
            return false;
        if (dateModified != null ? !dateModified.equals(kaizen.dateModified) : kaizen.dateModified != null)
            return false;
        if (problemStatement != null ? !problemStatement.equals(kaizen.problemStatement) : kaizen.problemStatement != null)
            return false;
        if (overProductionWaste != null ? !overProductionWaste.equals(kaizen.overProductionWaste) : kaizen.overProductionWaste != null)
            return false;
        if (transportationWaste != null ? !transportationWaste.equals(kaizen.transportationWaste) : kaizen.transportationWaste != null)
            return false;
        if (motionWaste != null ? !motionWaste.equals(kaizen.motionWaste) : kaizen.motionWaste != null)
            return false;
        if (waitingWaste != null ? !waitingWaste.equals(kaizen.waitingWaste) : kaizen.waitingWaste != null)
            return false;
        if (processingWaste != null ? !processingWaste.equals(kaizen.processingWaste) : kaizen.processingWaste != null)
            return false;
        if (inventoryWaste != null ? !inventoryWaste.equals(kaizen.inventoryWaste) : kaizen.inventoryWaste != null)
            return false;
        if (defectsWaste != null ? !defectsWaste.equals(kaizen.defectsWaste) : kaizen.defectsWaste != null)
            return false;
        if (rootCauses != null ? !rootCauses.equals(kaizen.rootCauses) : kaizen.rootCauses != null)
            return false;
        return !(imageFiles != null ? !imageFiles.equals(kaizen.imageFiles) : kaizen.imageFiles != null);

    }

    /**
     * generate a unique hash code for the current Kaizen object
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = itemID;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (dept != null ? dept.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateModified != null ? dateModified.hashCode() : 0);
        result = 31 * result + (problemStatement != null ? problemStatement.hashCode() : 0);
        result = 31 * result + (overProductionWaste != null ? overProductionWaste.hashCode() : 0);
        result = 31 * result + (transportationWaste != null ? transportationWaste.hashCode() : 0);
        result = 31 * result + (motionWaste != null ? motionWaste.hashCode() : 0);
        result = 31 * result + (waitingWaste != null ? waitingWaste.hashCode() : 0);
        result = 31 * result + (processingWaste != null ? processingWaste.hashCode() : 0);
        result = 31 * result + (inventoryWaste != null ? inventoryWaste.hashCode() : 0);
        result = 31 * result + (defectsWaste != null ? defectsWaste.hashCode() : 0);
        result = 31 * result + (rootCauses != null ? rootCauses.hashCode() : 0);
        result = 31 * result + totalWaste;
        result = 31 * result + (imageFiles != null ? imageFiles.hashCode() : 0);
        result = 31 * result + (deleteMe ? 1 : 0);
        return result;
    }

    /**
     * generate a string representation of the current Kaizen
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        if(null != problemStatement) {
            if (problemStatement.equals(""))
                return "No problem statement";
            return problemStatement;
        }
        return "No problem Statement";
    }

    /**
     * remove any ImageFiles which have been deleted from the filesystem
     * @// FIXME: 3/14/2016 should be moved to datamanager. files will still be listed in database
     *
     * @return the number of files removed
     */
    @Deprecated
    public int removeDeletedImageFiles() {
        int numRemoved = 0;
        int i = 0;
        File fd;

        while(i < imageFiles.size()) {
            fd = new File(imageFiles.get(i).getPath());
            if(!fd.exists()) {
                imageFiles.remove(i);
                numRemoved++;
            }
            else
                i++;
        }
        return numRemoved;
    }

    /**
     * get an ImageFile using its id
     *
     * @param id the id of the requested ImageFile
     * @return the requested ImageFile
     */
    public ImageFile getImage(int id) {
        if(id < 0)
            return null;

        int i = 0;
        int size = imageFiles.size();
        while(id != imageFiles.get(i).getItemID()) {
            i++;
            if(size <= i)
                return null;
        }
        return imageFiles.get(i);
    }
}
