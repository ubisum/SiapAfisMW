package it.mgg.siapafismw.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.model.Detenuto;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.model.MatricolaTMiddle;
import it.mgg.siapafismw.repositories.DetenutoRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;
import it.mgg.siapafismw.repositories.MatricolaTMiddleRepository;
import it.mgg.siapafismw.service.DetenutoServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DetenutoDAOImpl implements DetenutoDAO 
{
	@Autowired
	private DetenutoRepository detenutoRepository;
	
	@Autowired
	private FamiliareRepository familiareRepository;
	
	@Autowired
	private MatricolaTMiddleRepository matricolaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(DetenutoDAOImpl.class);
	
	@Override
	public DetenutoDTO findDetenutoByMatricola(String matricola) 
	{
		/* controllo presenza matricola */
		if(StringUtils.isBlank(matricola))
		{
			logger.info("Il valore della matricola fornito non e' valido");
			throw new IllegalArgumentException("Il valore della matricola fornito non e' valido");
		}
		
		/* esecuzione query */
		String query = "SELECT  "
				+ "  DISTINCT ma.M304_TIPO,  "
				+ "  ms.M150_NOME,  "
				+ "  MS.M150_COGN,  "
				+ "  mm.M00_MAT,  "
				+ "  mi.M155_ID_ISTITUTO,  "
				+ "  MS2.M157_DEN  "
				+ "FROM  "
				+ "  GATEWAY.MDD00_MATRICOLA mm  "
				+ "  JOIN GATEWAY.MDD150_SOGGETTO ms ON ms.M150_ID_SOGG = mm.M00_ID_SOGG  "
				+ "  JOIN GATEWAY.MDD166_DISLOCAZ md ON md.M166_MAT = mm.M00_MAT  "
				+ "  JOIN GATEWAY.MDD161_CELLA mc ON md.M166_ID_CELLA = mc.M161_ID_CELLA  "
				+ "  JOIN GATEWAY.MDD155_ISTITUTO mi ON mc.M161_ID_ISTITUTO = mi.M155_ID_ISTITUTO  "
				+ "  JOIN GATEWAY.MDD157_SEZIONE ms2 ON ms2.M157_ID_SEZIONE = mc.M161_ID_SEZIONE  "
				+ "  JOIN GATEWAY.MDD304_AUTORIZZ ma ON ma.M304_MAT = mm.M00_MAT  "
				+ "WHERE  "
				+ "  mm.M00_MAT = :matricola  "
				+ "  AND ma.M304_FUNZ = 'C'  "
				+ "  AND ma.M304_STATO = 'V'  "
				+ "  AND mm.M00_DT_CARC_CHIUSA IS NULL  "
				+ "  AND md.M166_PROG_DISL = ( "
				+ "    SELECT  "
				+ "      MAX(md2.M166_PROG_DISL)  "
				+ "    FROM  "
				+ "      GATEWAY.MDD166_DISLOCAZ md2  "
				+ "    WHERE  "
				+ "      MD2.M166_MAT = md.M166_MAT "
				+ "  )  "
				+ "  AND ( "
				+ "    mm.M00_DT_CANC IS NULL  "
				+ "    AND ms.M150_DT_CANC IS NULL  "
				+ "    AND MD.M166_DT_CANC IS NULL  "
				+ "    AND MS2.M157_DT_CANC IS NULL  "
				+ "    AND ma.M304_DT_CANC IS NULL "
				+ "  ) "
				+ "";
		
		logger.info("Esecuzione query nativa...");
		List<Object[]> listaRisultati = entityManager.createNativeQuery(query).setParameter("matricola", matricola).getResultList();
		
		/* controllo presenza risultati */
		if(CollectionUtils.isEmpty(listaRisultati))
		{
			logger.info("Nessun detenuto trovato con la matricola fornita {}", matricola);
			throw new IllegalStateException("Nessun detenuto trovato con la matricola fornita");
		}
		
		logger.info("Numero risultati trovati: {}", listaRisultati.size());
		
		/* costruzione detenuto */
		DetenutoDTO detenuto = null;
		
		logger.info("Preparazione oggetto di output...");
		for(Object[] row : listaRisultati)
		{
			String tipoAutorizzazione = row[0] != null ? String.valueOf((char)row[0]) : null;
			
			if(detenuto == null)
			{
				detenuto = new DetenutoDTO();
				detenuto.setNome((String)row[1]);
				detenuto.setCognome((String)row[2]);
				detenuto.setMatricola((String)row[3]);
				detenuto.setPenitenziario((String)row[4]);
				detenuto.setSezione((String)row[5]);
			}
			
			if("P".equalsIgnoreCase(tipoAutorizzazione))
			{
				detenuto.setAvailable(true);
				break;
			}
		}
		
		logger.info("Terminata preparazione oggetto di output, con flag available = {}", detenuto.isAvailable());

		return detenuto;
		
	}

	@Override
	public List<Detenuto> getAllDetenuti() 
	{
		/* ricerca di tutti i detenuti presenti sul database */
		return detenutoRepository.findAll();
	}

	@Override
	public List<Detenuto> getDetenutiByNumeroTelefono(String numeroTelefono) 
	{
		/* controlli input */
		if(StringUtils.isBlank(numeroTelefono))
			throw new IllegalArgumentException("Il numero di telefono fornito non e' valido");
		
		/* ricerca detenuto */
		Optional<Familiare> familiare = familiareRepository.findById(numeroTelefono);
		if(familiare.isEmpty())
			throw new IllegalArgumentException("Nessun familiare trovato con il numero di telefono specificato");
		
		return familiare.get().getListaDetenuti();
	}

	@Override
	public List<Detenuto> findDetenutiByCFNumeroTelefono(RicercaDTO ricerca) 
	{
		logger.info("Accesso alla funzione DAO per la ricerca dei detenuti in base al CF "
				  + "od al numero telefonico del familiare...");
		
		/* controlli */
		if(ricerca == null)
		{
			logger.info("DTO di ricerca non presente");
			throw new IllegalArgumentException("DTO di ricerca non presente");
		}
		
		if(StringUtils.isBlank(ricerca.getCodiceFiscaleFamiliare()) && 
		   StringUtils.isBlank(ricerca.getNumeroTelefonoFamiliare()))
		{
			logger.info("Impossibile effettuare la ricerca: fornire uno tra codice fiscale e numero di telefono del familiare");
			throw new IllegalArgumentException("Impossibile effettuare la ricerca: fornire uno tra codice fiscale e numero di telefono del familiare");
		}
		
		/* definizione query */
		String query = null;
		
		if(StringUtils.isNotBlank(ricerca.getCodiceFiscaleFamiliare()))
			query = "SELECT "
					+ "  DISTINCT ms.M150_NOME, "
					+ "  MS.M150_COGN, "
					+ "  mi.M155_ID_ISTITUTO, "
					+ "  MS2.M157_DEN, "
					+ "  mm.M00_MAT, "
					+ "  MF.* "
					+ "FROM "
					+ "  GATEWAY.MDD301_FAMILIARE mf "
					+ "  JOIN GATEWAY.MDD150_SOGGETTO ms ON mf.M301_ID_SOGG = ms.M150_ID_SOGG "
					+ "  JOIN GATEWAY.MDD00_MATRICOLA mm ON mm.M00_ID_SOGG = MS.M150_ID_SOGG "
					+ "  JOIN GATEWAY.MDD166_DISLOCAZ md ON mm.M00_MAT = MD.M166_MAT "
					+ "  JOIN GATEWAY.MDD161_CELLA mc ON MC.M161_ID_CELLA = MD.M166_ID_CELLA "
					+ "  JOIN GATEWAY.MDD160_REPARTO mr ON mr.M160_ID_REPARTO = MC.M161_ID_REPARTO "
					+ "  JOIN GATEWAY.MDD157_SEZIONE ms2 ON MS2.M157_ID_SEZIONE = mm.M00_C_SEZ_APP "
					+ "  JOIN GATEWAY.MDD155_ISTITUTO mi ON mi.M155_ID_ISTITUTO = MC.M161_ID_ISTITUTO "
					+ "WHERE "
					+ "  mf.M301_COD_FISCALE = :codiceFiscale";
		
		else
			query = "SELECT  DISTINCT "
					+ "  ms.M150_NOME,  "
					+ "  MS.M150_COGN,  "
					+ "  mi.M155_ID_ISTITUTO,  "
					+ "  MS2.M157_DEN,  "
					+ "  mm.M00_MAT,  "
					+ "  MF.*  "
					+ "FROM  "
					+ "  GATEWAY.MDD301_FAMILIARE mf  "
					+ "  JOIN GATEWAY.MDD150_SOGGETTO ms ON mf.M301_ID_SOGG = ms.M150_ID_SOGG  "
					+ "  JOIN GATEWAY.MDD00_MATRICOLA mm ON mm.M00_ID_SOGG = MS.M150_ID_SOGG  "
					+ "  JOIN GATEWAY.MDD166_DISLOCAZ md ON mm.M00_MAT = MD.M166_MAT  "
					+ "  JOIN GATEWAY.MDD161_CELLA mc ON MC.M161_ID_CELLA = MD.M166_ID_CELLA  "
					+ "  JOIN GATEWAY.MDD160_REPARTO mr ON mr.M160_ID_REPARTO = MC.M161_ID_REPARTO  "
					+ "  JOIN GATEWAY.MDD157_SEZIONE ms2 ON MS2.M157_ID_SEZIONE = mm.M00_C_SEZ_APP  "
					+ "  JOIN GATEWAY.MDD155_ISTITUTO mi ON mi.M155_ID_ISTITUTO = MC.M161_ID_ISTITUTO  "
					+ "WHERE  "
					+ "  MF.M301_UTENZA = :utenza  "
					+ "  AND MM.M00_DT_CARC_CHIUSA IS NULL  "
					+ "  AND md.M166_PROG_DISL = ( "
					+ "    SELECT  "
					+ "      MAX(md2.M166_PROG_DISL)  "
					+ "    FROM  "
					+ "      GATEWAY.MDD166_DISLOCAZ md2  "
					+ "    WHERE  "
					+ "      MD2.M166_MAT = md.M166_MAT "
					+ "  )  "
					+ "  AND( "
					+ "    mf.M301_DT_CANC IS NULL  "
					+ "    AND ms.M150_DT_CANC IS NULL  "
					+ "    AND mm.M00_DT_CANC IS NULL  "
					+ "    AND md.M166_DT_CANC IS NULL  "
					+ "    AND mc.M161_DT_CANC IS NULL  "
					+ "    AND mr.M160_DT_CANC IS NULL  "
					+ "    AND MS2.M157_DT_CANC IS NULL  "
					+ "    AND mi.M155_D_CHIU IS NULL "
					+ "  ) "
					+ "";
		
		/* preparazione output */
		List<Object[]> listaOutput = null; 
		List<Detenuto> listaDetenuti = new ArrayList<>();		
		
		/* inserimento parametri */
		if(StringUtils.isNotBlank(ricerca.getCodiceFiscaleFamiliare()))
		{
			logger.info("Preparazione query per ricerca in base al CF...");
			listaOutput = entityManager.createNativeQuery(query).setParameter("codiceFiscale", ricerca.getCodiceFiscaleFamiliare()).getResultList();
		}
		
		else
		{
			logger.info("Preparazione query per ricerca in base al numero di telefono...");
			listaOutput = entityManager.createNativeQuery(query).setParameter("utenza", ricerca.getNumeroTelefonoFamiliare()).getResultList();
		}
			
		/* creazione lista detenuti */
		if(CollectionUtils.isNotEmpty(listaOutput))
		{
			logger.info("Creazione lista di {} detenuti...", listaOutput.size());
			
			for(Object[] array : listaOutput)
			{
				Detenuto detenuto = new Detenuto();
				detenuto.setNome((String)array[0]);
				detenuto.setCognome((String)array[1]);
				detenuto.setPenitenziario((String)array[2]);
				detenuto.setSezione((String)array[3]);
				detenuto.setMatricola((String)array[4]);
				
				listaDetenuti.add(detenuto);
				
			}
		}
		
		else
			logger.info("Nessun detenuto trovato");
		
		return listaDetenuti;
		
	}

	@Override
	public Integer findIdSoggettoFromMatricola(String matricola) 
	{
		/* controllo matricola */
		if(StringUtils.isBlank(matricola))
			throw new IllegalArgumentException("Matricola detenuto non valido");
		
		Optional<MatricolaTMiddle> idSoggetto = this.matricolaRepository.findById(matricola);
		
		return idSoggetto.isEmpty() ? null : idSoggetto.get().getIdSoggetto();
	}

}
