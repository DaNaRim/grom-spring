package com.lesson3.homework.model;

import com.lesson3.homework.exceptions.BadRequestException;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "STORAGE")
public class Storage {
    private long id;
    private List<File> files;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageSize;

    public Storage() {
    }

    public Storage(String[] formatsSupported, String storageCountry, long storageSize) throws BadRequestException {

        if (storageSize <= 0) throw new BadRequestException("storageSize must be > 0");

        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    @Id
    @SequenceGenerator(name = "storageSeq", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storageSeq")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Transient
    public String[] getArrayFormatsSupported() {
        return formatsSupported;
    }

    @Column(name = "FORMATS_SUPPORTED")
    public String getFormatsSupported() {

        StringBuilder formatsSupported = new StringBuilder();

        for (String str : getArrayFormatsSupported()) {
            formatsSupported.append(str).append(", ");
        }
        formatsSupported.delete(formatsSupported.lastIndexOf(", "), formatsSupported.length());

        return formatsSupported.toString();
    }

    @Column(name = "COUNTRY")
    public String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "STORAGE_SIZE")
    public long getStorageSize() {
        return storageSize;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFormatsSupported(String formatsSupported) {
        this.formatsSupported = formatsSupported.split(", ");
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                '}';
    }
}