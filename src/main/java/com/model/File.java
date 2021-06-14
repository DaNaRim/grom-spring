package com.model;

import javax.persistence.*;

@Entity
@Table(name = "FILES")
public class File {
    private long id;
    private String name;
    private String format;
    private long size;
    private Storage storage;

    public File() {
    }

    public File(String name, String format, long size) {
        this.name = name;
        this.format = format;
        this.size = size;
    }

    @Id
    @SequenceGenerator(name = "fileSeq", sequenceName = "FILES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileSeq")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FORMAT")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Column(name = "FILE_SIZE")
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @ManyToOne
    @JoinColumn(name = "STORAGE_ID")
    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storage=" + storage +
                '}';
    }
}