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
public class Kaizen {

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
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
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
        this.problemStatement = problemStatement;
    }

    public String getOverProductionWaste() {
        return overProductionWaste;
    }

    public void setOverProductionWaste(String overProductionWaste) {
        this.overProductionWaste = overProductionWaste;
    }

    public String getTransportationWaste() {
        return transportationWaste;
    }

    public void setTransportationWaste(String transportationWaste) {
        this.transportationWaste = transportationWaste;
    }

    public String getWaitingWaste() {
        return waitingWaste;
    }

    public void setWaitingWaste(String waitingWaste) {
        this.waitingWaste = waitingWaste;
    }

    public String getProcessingWaste() {
        return processingWaste;
    }

    public void setProcessingWaste(String processingWaste) {
        this.processingWaste = processingWaste;
    }

    public String getInventoryWaste() {
        return inventoryWaste;
    }

    public void setInventoryWaste(String inventoryWaste) {
        this.inventoryWaste = inventoryWaste;
    }

    public String getDefectsWaste() {
        return defectsWaste;
    }

    public void setDefectsWaste(String defectsWaste) {
        this.defectsWaste = defectsWaste;
    }

    public String getRootCauses() {
        return rootCauses;
    }

    public void setRootCauses(String rootCauses) {
        this.rootCauses = rootCauses;
    }

    public int getTotalWaste() {
        return totalWaste;
    }

    public void setTotalWaste(int totalWaste) {
        this.totalWaste = totalWaste;
    }

    public String getMotionWaste() {
        return motionWaste;
    }

    public void setMotionWaste(String motionWaste) {
        this.motionWaste = motionWaste;
    }


    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Image addImage(Image image) {
        images.add(image);
        return images.get(images.size());
    }

    public void removeImage(int i) {
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

        Kaizen test = new Kaizen("Ian M", "CS", today, today,
                "Make sure your problem statement is not a root cause!",
                "We made too much!", "She had to carry it too far!",
                "I had to put it away and get it out again",
                "We had to wait for them to finish!",
                "They don't need us to do this!", "He ran out of parts!", "I damaged the product!",
                "1) this definitely had something to do with it\n2) this might have caused it\n3) maybe this is the issue?", 365, null);
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
}
