package designpattern.structural.facade;

interface File {
    void save();
}

interface Codec{

}

class VideoFile implements File{
    private String filename;

    public VideoFile(String filename) {
        this.filename = filename;
    }
    public VideoFile(byte[] file){

    }
    @Override
    public void save() {
        System.out.println("file saved.");
    }
}

class OggCompressionCodec implements Codec{
}

class MPEG4CompressionCodec implements Codec{
}

class CodecFactory {
    public static Codec extract(VideoFile file){
        return new MPEG4CompressionCodec();
    }
}

class BitrateReader {
    public static byte[] read(String filename,Codec sourceCodec){
        return "sample source bitrate".getBytes();
    }
    public static byte[] convert(byte[] buffer,Codec destinationCodec)
    {
        return "sample target bitrate".getBytes();
    }
}

class AudioMixer {
    public byte[] fix(byte[] video){
        return "fixed multimedia".getBytes();
    }
}

class VideoConverter {
    public File convert(String filename, String format) {
        System.out.println(String.format("Converting file: '%s' ...",filename));
        VideoFile file = new VideoFile(filename);
        Codec sourceCodec=CodecFactory.extract(file);
        Codec destinationCodec;
        if(format=="mp4"){
            destinationCodec=new MPEG4CompressionCodec();
        }else{
            destinationCodec = new OggCompressionCodec();
        }
        byte[] buffer = BitrateReader.read(filename,sourceCodec);
        byte[] result = BitrateReader.convert(buffer,destinationCodec);
        result = (new AudioMixer()).fix(result);
        System.out.println("video Conversion completed");
        return new VideoFile(result);
    }
}

public class FacadePattern {
    public static void demo(){
        VideoConverter converter=new VideoConverter();
        File mp4=converter.convert("youtubevideo.ogg", "mp4");
        mp4.save();
    }
}
