package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ID_DATA_VERSE_MAPPING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IDDataVerseMapping 
{
	@Id
	@Column(name = "ID_COLLOQUIO_DATA_VERSE")
	private String idColloquioDataVerse;
	
	@Column(name = "MATRICOLA")
	private String matricola;
	
	@Column(name = "PRG_COLLOQUIO_FAM")
	private Integer prgColloquioFam;
	
	@Column(name = "PRG_COLLOQUIO")
	private Integer prgColloquio;
}
