package com.model;

import javax.persistence.*;

@Entity
@Table(name = "STORAGE")
public class Storage {
    private long id;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageSize;

    public Storage() {
    }

    public Storage(String[] formatsSupported, String storageCountry, long storageSize) {
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

    public void setId(long id) {
        this.id = id;
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

    public void setFormatsSupported(String formatsSupported) {
        this.formatsSupported = formatsSupported.split(", ");
    }

    @Column(name = "COUNTRY")
    public String getStorageCountry() {
        return storageCountry;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    @Column(name = "STORAGE_SIZE")
    public long getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }
}