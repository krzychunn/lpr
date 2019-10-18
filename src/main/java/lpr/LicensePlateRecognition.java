package lpr;

import com.openalpr.jni.*;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LicensePlateRecognition {

    private final String OPENCV_LIBRARY_PATH = "\\opencv_java343.dll";
    private final String ROOT_VIDEO_FILE_PATH = "\\video\\";
    private final String ROOT_FRAMES_FILE_PATH = "\\frames\\";
    private final String ROOT_STATISTICS_FILE_PATH = "\\statistics\\";
    private final String OPENALPR_METHOD_NAME = "OpenAlpr";
    private Map<String, Integer> registrationPlates;

    public LicensePlateRecognition() throws IOException {
    }

    public void run() {

        System.load(System.getProperty("user.dir") + OPENCV_LIBRARY_PATH);

        writeHeaderInTimesFile(OPENALPR_METHOD_NAME);

        List<File> videoFileAbsolutePaths = getListOfVideoFiles();
        for(File videoFileAbsolutePath : videoFileAbsolutePaths){
            captureVideo(videoFileAbsolutePath);
        }

    }

    public List<File> getListOfVideoFiles(){

        File f = new File(System.getProperty("user.dir") + ROOT_VIDEO_FILE_PATH);
        File[] allSubFiles = f.listFiles();
        List<File> videoFileAbsolutePaths = new ArrayList<>();
        for (File file : allSubFiles) {
                videoFileAbsolutePaths.add(file);
                //steps for files
        }
        return videoFileAbsolutePaths;
    }

    public void captureVideo(File file) {

        registrationPlates = new LinkedHashMap<>();

        VideoCapture capture = new VideoCapture();
        capture.open(file.getAbsolutePath());
        Mat frame = new Mat();

        int i = 0;
        int framesDivider = 12;
        long startTime = System.currentTimeMillis();
        for (; ; ) {
            if (capture.read(frame)) {
                if (i % framesDivider == 0) {
                    testOpenAlpr(frame, i);
                }
                i++;
            } else {
                long endTime = System.currentTimeMillis();
                long timeOfProcessing = endTime - startTime;
                writeDataInTimesFile(file.getName(), timeOfProcessing, OPENALPR_METHOD_NAME);
                writeRecognitionsToFiles(registrationPlates, file.getName(), OPENALPR_METHOD_NAME);
                break;
            }
        }
    }

    public void testOpenAlpr(Mat matrix, int frameNumber) {

        Alpr alpr = new Alpr("eu", "openalpr.conf", "runtime_data");

        int topBestPlates = 1;
        alpr.setTopN(topBestPlates);

        AlprResults results = null;
        try {
            results = alpr.recognize(matToByteArray(matrix));
        } catch (AlprException e) {
            e.printStackTrace();
        }

        for (AlprPlateResult result : results.getPlates()) {
            AlprPlate plate = result.getBestPlate();
            registrationPlates.put(plate.getCharacters(), frameNumber);
        }

        alpr.unload();
    }

    public byte[] matToByteArray(Mat matrix) {

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        return mob.toArray();
    }

    public void writeHeaderInTimesFile(String methodName) {

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + ROOT_STATISTICS_FILE_PATH  + methodName + "_stats" + ".csv", true);

            fileWriter.write("VideoFileName" + "," + "TimeOfProcessing");
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataInTimesFile(String videoFileName, long timeOfProcessing, String methodName) {

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + ROOT_STATISTICS_FILE_PATH  + methodName + "_stats" + ".csv", true);

            fileWriter.write(videoFileName + "," + timeOfProcessing);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRecognitionsToFiles(Map<String, Integer> registrationPlates, String videoFileName, String methodName) {

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + ROOT_FRAMES_FILE_PATH + methodName + "\\" + videoFileName.replace(".AVI", "") + "_KV" + ".csv", true);

            fileWriter.write("RegistrationPlateNumber" + "," + "FrameNumber");
            fileWriter.write("\n");
            for (Map.Entry<String, Integer> entry : registrationPlates.entrySet()) {
                fileWriter.write(entry.getKey() + "," + entry.getValue());
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + ROOT_FRAMES_FILE_PATH + methodName + "\\" + videoFileName.replace(".AVI", "") + "_VK" + ".csv", true);

            fileWriter.write("FrameNumber" + "," + "RegistrationPlateNumber");
            fileWriter.write("\n");
            for (Map.Entry<String, Integer> entry : registrationPlates.entrySet()) {
                fileWriter.write(entry.getValue() + "," + entry.getKey());
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
