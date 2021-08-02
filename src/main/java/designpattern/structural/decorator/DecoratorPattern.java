package designpattern.structural.decorator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

interface DataSource {
    void writeData(String data);

    String readData();
}

class FileDataSource implements DataSource {
    private String filename;
    private String data;

    public FileDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public void writeData(String data) {
        System.out.println(String.format("Write data: %s to file: %s", data, this.filename));
        this.data = data;
    }

    @Override
    public String readData() {
        System.out.println(String.format("read data: '%s' from file: %s", this.data, this.filename));
        return this.data;
    }
}

class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;

    public DataSourceDecorator() {
    }

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data) {
        this.wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return this.wrappee.readData();
    }
}

class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    private String Encrypt(String data) {
        try {
            return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String Decrypt(String data) {
        try {
            byte[] decodedValue = Base64.getDecoder().decode(data);
            return new String(decodedValue, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void writeData(String data) {
        super.writeData(this.Encrypt(data));
    }

    @Override
    public String readData() {
        String encryptedData = super.readData();
        return this.Decrypt(encryptedData);
    }
}

class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }

    private String Compress(String data) {
        try {
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(data.getBytes("UTF-8"));
            gzip.close();
            return obj.toString("UTF-8");// .toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String Decompress(String data) {
        try {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data.getBytes()));
            BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
            return bf.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void writeData(String data) {
        super.writeData(this.Compress(data));
    }

    @Override
    public String readData() {
        String compressedData = super.readData();
        return this.Decompress(compressedData);
    }
}

class Application {
    public void dumbUsageExample() {
        DataSource source = new FileDataSource("storage.dat");
        source.writeData("Salary Records");

        DataSource source2 = new CompressionDecorator(source);
        source2.writeData("Salary Records");

        DataSource source3 = new EncryptionDecorator(source);
        source3.writeData("Salary Records");
    }
}

class SalaryManager {
    private DataSource source;

    public SalaryManager(DataSource source) {
        this.source = source;
    }

    public String load() {
        return this.source.readData();
    }

    public void save() {
        this.source.writeData("Salary Records");
    }
}

public class DecoratorPattern {
    public static void demo() {
        DataSource source = new FileDataSource("salary.dat");
        Scanner inputDevice = new Scanner(System.in);
        System.out.print("enable encryption?(yes/no):");
        if (inputDevice.next().equals("yes")) {
            source = new EncryptionDecorator(source);
        }
        System.out.print("enable compression?(yes/no):");
        if (inputDevice.next().equals("yes")) {
            source = new CompressionDecorator(source);
        }
        inputDevice.close();
        source.writeData("Sample Salary");
        SalaryManager logger = new SalaryManager(source);
        String salary = logger.load();
        System.out.println("Salary is: " + salary);
    }
}
