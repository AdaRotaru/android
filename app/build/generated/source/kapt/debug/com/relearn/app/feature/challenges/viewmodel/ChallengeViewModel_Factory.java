package com.relearn.app.feature.challenges.viewmodel;

import com.relearn.app.feature.challenges.domain.repository.IChallengeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ChallengeViewModel_Factory implements Factory<ChallengeViewModel> {
  private final Provider<IChallengeRepository> repositoryProvider;

  public ChallengeViewModel_Factory(Provider<IChallengeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ChallengeViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ChallengeViewModel_Factory create(
      Provider<IChallengeRepository> repositoryProvider) {
    return new ChallengeViewModel_Factory(repositoryProvider);
  }

  public static ChallengeViewModel newInstance(IChallengeRepository repository) {
    return new ChallengeViewModel(repository);
  }
}
