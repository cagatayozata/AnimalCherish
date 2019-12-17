package com.team1.animalproject.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = Include.NON_NULL)
public class KullaniciPrincipal implements Serializable {

	private static final long serialVersionUID = 1810854384164649482L;

	private String id;

	private List<String> yetkiler;

	private String smth;

}
 