package TestFileUpload;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImageMapper {


    @Select("SELECT id, image_link, uuid as imageUuid, image_name, saved_date as imageDate  FROM image_storage")
    List<Image> findAll();

    @Insert("insert into image_storage(image_link, uuid, image_name) values (#{imageLink}, #{imageUuid}, #{imageName})")
    void insertImageInfo(Image image);

    @Select("SELECT image_link from image_storage where id = #{id}")
    String findImageByImageLink(String id);

    @Select("SELECT uuid as imageUuid from image_storage where id = #{id}")
    String findImageByUuid(String id);

    @Delete("DELETE FROM image_storage WHERE id = #{id}")
    void deleteImage(String id);

}
