package pe.isil.service;

import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public interface S3Manager {

    List<Bucket> listAllBuckets();
    boolean createBucket(String bucketName);
    boolean createObject(String bucketName, String source, String target);
    boolean downloadObject(String bucketName, String source, String target);
    void deleteObject(String bucketName, String source);
    void deleteBucket(String bucketName);

}
