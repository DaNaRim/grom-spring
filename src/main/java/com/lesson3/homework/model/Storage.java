package com.lesson3.homework.model;

import com.lesson3.homework.exceptions.BadRequestException;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Table(name = "STORAGE")
public class Storage {
    private long id;
    private HashSet<File> files;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageSize;
    private long freeSpace;

    public Storage() {
    }

    public Storage(String[] formatsSupported, String storageCountry, long storageSize) throws BadRequestException {
        if (formatsSupported == null || formatsSupported.length == 0 || storageCountry == null ||
                storageCountry.equals("") || storageSize <= 0) {
            throw new BadRequestException("Fields are not filed correctly");
        }
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
        this.freeSpace = storageSize;
    }

    public Storage(long id, HashSet<File> files, String[] formatsSupported, String storageCountry, long storageSize, long freeSpace) {
        this.id = id;
        this.files = files;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
        this.freeSpace = freeSpace;
    }

    @Id
    @SequenceGenerator(name = "storageSeq", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storageSeq")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "FILES")
    public HashSet<File> getFiles() {
        return files;
    }

    @Transient
    public String[] getTRFormatsSupported() {
        return formatsSupported;
    }

    @Column(name = "FORMATS_SUPPORTED")
    public String getFormatsSupported() {

        StringBuilder formatsSupported = new StringBuilder();

        for (String str : getTRFormatsSupported()) {
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

    @Column(name = "FREE_SPACE")
    public long getFreeSpace() {
        return freeSpace;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFiles(HashSet<File> files) {
        this.files = files;
    }

    public void setFormatsSupported(String[] formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }
}