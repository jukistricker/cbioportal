package org.cbioportal.legacy.persistence.virtualstudy;

import org.cbioportal.legacy.persistence.AlterationRepository;
import org.cbioportal.legacy.persistence.ClinicalAttributeRepository;
import org.cbioportal.legacy.persistence.ClinicalDataRepository;
import org.cbioportal.legacy.persistence.ClinicalEventRepository;
import org.cbioportal.legacy.persistence.GenericAssayRepository;
import org.cbioportal.legacy.persistence.MolecularProfileRepository;
import org.cbioportal.legacy.persistence.PatientRepository;
import org.cbioportal.legacy.persistence.SampleRepository;
import org.cbioportal.legacy.persistence.StudyRepository;
import org.cbioportal.legacy.service.VirtualStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConditionalOnProperty(name = "vs_mode", havingValue = "true")
public class VSAwareRepositoriesConfiguration {

  @Autowired VirtualStudyService virtualStudyService;

  @Primary
  @Bean
  public StudyRepository studyRepository(StudyRepository studyRepository) {
    return new VSAwareStudyRepository(virtualStudyService, studyRepository);
  }

  @Primary
  @Bean
  VirtualStudyService virtualStudiesServiceWithSilencedPublishedVirtualStudies() {
    return new SilencedPublishedVSService(virtualStudyService);
  }

  @Primary
  @Bean
  ClinicalAttributeRepository clinicalAttributeRepository(
      ClinicalAttributeRepository clinicalAttributeRepository) {
    return new VSAwareClinicalAttributeRepository(virtualStudyService, clinicalAttributeRepository);
  }

  @Primary
  @Bean
  public VSAwareClinicalDataRepository clinicalDataRepository(
      ClinicalDataRepository clinicalDataRepository) {
    return new VSAwareClinicalDataRepository(virtualStudyService, clinicalDataRepository);
  }

  @Primary
  @Bean
  public MolecularProfileRepository molecularProfileRepository(
      MolecularProfileRepository molecularProfileRepository) {
    return new VSAwareMolecularProfileRepository(virtualStudyService, molecularProfileRepository);
  }

  @Primary
  @Bean
  public SampleRepository sampleRepository(SampleRepository sampleRepository) {
    return new VSAwareSampleRepository(virtualStudyService, sampleRepository);
  }

  @Primary
  @Bean
  public VSAwareGenericAssayRepository genericAssayRepository(
      GenericAssayRepository genericAssayRepository) {
    return new VSAwareGenericAssayRepository(virtualStudyService, genericAssayRepository);
  }

  @Primary
  @Bean
  public VSAwareAlterationRepository alterationRepository(
      AlterationRepository alterationRepository) {
    return new VSAwareAlterationRepository(virtualStudyService, alterationRepository);
  }

  @Primary
  @Bean
  public VSAwarePatientRepository patientRepository(PatientRepository patientRepository) {
    return new VSAwarePatientRepository(virtualStudyService, patientRepository);
  }

  @Primary
  @Bean
  public VSAwareClinicalEventRepository clinicalEventRepository(
      ClinicalEventRepository clinicalEventRepository,
      VSAwarePatientRepository vsAwarePatientRepository) {
    return new VSAwareClinicalEventRepository(
        virtualStudyService, clinicalEventRepository, vsAwarePatientRepository);
  }
}
