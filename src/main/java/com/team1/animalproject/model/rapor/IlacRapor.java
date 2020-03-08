package com.team1.animalproject.model.rapor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString(callSuper = true)
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class IlacRapor implements Serializable {

    private static final long serialVersionUID = -7901480885830007004L;

    public String urunAdi = "";
    public String urunAdet = "";
    public String sistemNo = "";
    public String kullanimSekli = "";
}


