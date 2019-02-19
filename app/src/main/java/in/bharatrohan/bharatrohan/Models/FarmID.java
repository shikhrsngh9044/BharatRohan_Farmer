package in.bharatrohan.bharatrohan.Models;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class FarmID {

    private String farmid;
    @SerializedName("kml_file")
    private File file;

    public FarmID() {
    }

    public FarmID(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
