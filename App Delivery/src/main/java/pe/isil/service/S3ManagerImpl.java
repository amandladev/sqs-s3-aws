package pe.isil.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class S3ManagerImpl implements S3Manager{

    AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVDYFFTNLZ",
            "hYDk+VNIeqrb83VnIlyHD46l01lcpfaEVexzgvIO");

    AmazonS3 s3client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();

    @Override
    public List<Bucket> listAllBuckets() {
        return s3client.listBuckets();
    }

    @Override
    public boolean createBucket(String bucketName) {

        if (s3client.doesBucketExistV2(bucketName)) {
            System.out.println("El bucket " + bucketName + " ya existe!");
            return false;
        }

        s3client.createBucket(bucketName);
        System.out.println("El bucket " + bucketName + " se ha creado!");
        return true;
    }

    @Override
    public boolean createObject(String bucketName, String source, String target) {
        try {
            s3client.putObject(bucketName, target, new File(source));
            System.out.println("Se ha subido el archivo al bucket " + bucketName);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean downloadObject(String bucketName, String source, String target) {
        S3Object s3Object = s3client.getObject(bucketName, source);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();

        File targetFile = new File(target);

        try {
            java.nio.file.Files.copy(
                    objectContent,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Se ha descargado el archivo desde el bucket " + bucketName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteObject(String bucketName, String source) {
        s3client.deleteObject(bucketName, source);
        System.out.println("Se ha eliminado el archivo desde el bucket " + bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) {
        try {
            s3client.deleteBucket(bucketName);
            System.out.println("Se ha eliminado el bucket " + bucketName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
