package com.team1.animalproject.model.rapor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@ToString(callSuper = true)
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class IlacRecetesi implements Serializable {

    private static final long serialVersionUID = -7901480885830007004L;

    public String seriNo = "";
    public String sinifi = "";
    public String sifresi = "";
    public String veterinerAdi = "";
    public String diplomaNo = "";
    public String sicilNo = "";
    private String adres = "";
    private String sahipAd = "";
    public String sahipAdres = "";
    public String tur = "";
    public String isletmeNo = "";
    public String irk = "";
    public String tedaviBaslangicTarihi = "";
    public String teshis = "";
    public String kupeNumarasi = "";
    public String yas = "";
    public String cinsiyet = "";
    public String esgal;

    List<IlacRapor> ilacRaporList = Lists.newArrayList();
}


