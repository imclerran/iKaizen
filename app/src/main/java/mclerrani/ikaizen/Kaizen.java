package mclerrani.ikaizen;
import android.media.Image;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import com.google.gson.*;

/**
 * Created by Ian on 1/13/2016.
 *
 * @author Ian McLerran
 */
public class Kaizen implements Comparable<Kaizen> {

    private static int count;
    private int itemID;

    private String owner;
    private String dept;
    private Calendar dateCreated;
    private Calendar dateModified;
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
    private List<Image> images;

    public Kaizen() {
        dateCreated = Calendar.getInstance();
        dateCreated.set(Calendar.HOUR_OF_DAY,0);
        dateModified = Calendar.getInstance();
        dateModified.set(Calendar.HOUR_OF_DAY,0);
        totalWaste = 0;

        itemID = count++;
    }

    public Kaizen(String owner, String dept, Calendar dateCreated, Calendar dateModified, String problemStatement,
                  String overProductionWaste, String transportationWaste, String motionWaste, String waitingWaste,
                  String processingWaste, String inventoryWaste, String defectsWaste, String rootCauses, int totalWaste, List<Image> images) {
        this.owner = owner;
        this.dept = dept;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
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
        this.images = images;

        itemID = count++;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        updateDateModified();
        this.owner = owner;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        updateDateModified();
        this.dept = dept;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        updateDateModified();
        this.dateCreated = dateCreated;
    }

    public Calendar getDateModified() {
        return dateModified;
    }

    public void setDateModified(Calendar dateModified) {
        this.dateModified = dateModified;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        updateDateModified();
        this.problemStatement = problemStatement;
    }

    public String getOverProductionWaste() {
        return overProductionWaste;
    }

    public void setOverProductionWaste(String overProductionWaste) {
        updateDateModified();
        this.overProductionWaste = overProductionWaste;
    }

    public String getTransportationWaste() {
        return transportationWaste;
    }

    public void setTransportationWaste(String transportationWaste) {
        updateDateModified();
        this.transportationWaste = transportationWaste;
    }

    public String getWaitingWaste() {
        return waitingWaste;
    }

    public void setWaitingWaste(String waitingWaste) {
        updateDateModified();
        this.waitingWaste = waitingWaste;
    }

    public String getProcessingWaste() {
        return processingWaste;
    }

    public void setProcessingWaste(String processingWaste) {
        updateDateModified();
        this.processingWaste = processingWaste;
    }

    public String getInventoryWaste() {
        return inventoryWaste;
    }

    public void setInventoryWaste(String inventoryWaste) {
        updateDateModified();
        this.inventoryWaste = inventoryWaste;
    }

    public String getDefectsWaste() {
        return defectsWaste;
    }

    public void setDefectsWaste(String defectsWaste) {
        updateDateModified();
        this.defectsWaste = defectsWaste;
    }

    public String getRootCauses() {
        return rootCauses;
    }

    public void setRootCauses(String rootCauses) {
        updateDateModified();
        this.rootCauses = rootCauses;
    }

    public int getTotalWaste() {
        return totalWaste;
    }

    public void setTotalWaste(int totalWaste) {
        updateDateModified();
        this.totalWaste = totalWaste;
    }

    public String getMotionWaste() {
        return motionWaste;
    }

    public void setMotionWaste(String motionWaste) {
        updateDateModified();
        this.motionWaste = motionWaste;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        updateDateModified();
        this.images = images;
    }

    public int getItemID() { return itemID; }

    public void setItemID(int itemID) {
        updateDateModified();
        this.itemID = itemID; }

    public Image addImage(Image image) {
        updateDateModified();
        images.add(image);
        return images.get(images.size());
    }

    public void removeImage(int i) {
        updateDateModified();
        images.remove(i);
    }

    public String getDateCreatedAsString() {
        SimpleDateFormat formatter=new SimpleDateFormat("MM/DD/yyyy");
        String date = formatter.format(dateCreated.getTime());
        return date;
    }

    public String getDateModifiedAsString() {
        SimpleDateFormat formatter=new SimpleDateFormat("MMM/DD/yyyy");
        String date = formatter.format(dateModified.getTime());
        return date;
    }

    public void updateDateModified() {
        dateModified.set(Calendar.HOUR_OF_DAY, 0);
    }

    public static Kaizen getTestKaizen() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        Kaizen test = new Kaizen("Your Name", "N/A", today, today,
                "Make sure your problem statement is not a root cause!",
                "We made too much!", "She had to carry it too far!",
                "I had to put it away and get it out again",
                "We had to wait for them to finish!",
                "They don't need us to do this!", "He ran out of parts!", "I damaged the product!",
                "1) this definitely caused the problem\n2) this might have contributed to the issue\n3) This could be part of the problem", 365, null);
        return test;
    }

    public String toJson() {
        String jsonString = new Gson().toJson(this);
        return jsonString;
    }

    public static Kaizen fromJson(String jsonString) {
        Kaizen kaizen = new Gson().fromJson(jsonString, Kaizen.class);
        return kaizen;
    }

    @Override
    public int compareTo(Kaizen another) {
        return this.dateModified.compareTo(another.dateModified);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kaizen kaizen = (Kaizen) o;

        if (itemID != kaizen.itemID) return false;
        if (totalWaste != kaizen.totalWaste) return false;
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
        return !(images != null ? !images.equals(kaizen.images) : kaizen.images != null);

    }

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
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if(null != problemStatement) {
            if (problemStatement.equals(""))
                return "No problem Statement";
            return problemStatement;
        }
        return "No problem Statement";
    }
}
