package TestFileUpload;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public class Image {
    private int id;
    private String imageLink;
    private String imageUuid;
    private String imageName;
    private Date imageDate;

    public Image() {

    }

    public Image(String imageLink, String imageUuid, String imageName) {
        this.imageLink = imageLink;
        this.imageUuid = imageUuid;
        this.imageName = imageName;

    }

    public Image(int id, String imageLink, String imageUuid, String imageName, Date imageDate) {
        this.id = id;
        this.imageLink = imageLink;
        this.imageUuid = imageUuid;
        this.imageName = imageName;
        this.imageDate = imageDate;
    }
}

