package com.lesson3.homework.model;

import com.lesson3.homework.exceptions.BadRequestException;

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

    public File(String name, String format, long size) throws BadRequestException {

        if (size <= 0) throw new BadRequestException("Size must be > 0");
        if (name.length() > 10) throw new BadRequestException("Name length must be <= 10");

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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @Column(name = "FORMAT")
    public String getFormat() {
        return format;
    }

    @Column(name = "FILE_SIZE")
    public long getSize() {
        return size;
    }

    @ManyToOne()
    @JoinColumn(name = "STORAGE_ID", nullable = false)
    public Storage getStorage() {
        return storage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSize(long size) {
        this.size = size;
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