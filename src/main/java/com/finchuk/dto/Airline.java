package com.finchuk.dto;

import java.net.URI;
import java.util.Objects;

/**
 * Created by olexandr on 25.03.17.
 */
public class Airline {
    private Long companyId;
    private String companyName;
    private URI imgPath;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public URI getImgPath() {
        return imgPath;
    }

    public void setImgPath(URI imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airline that = (Airline) o;

        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(imgPath, that.imgPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, imgPath);
    }
}
