package com.relearn.app.core.di;

import com.google.firebase.firestore.FirebaseFirestore;
import com.relearn.app.feature.challenges.domain.repository.IChallengeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RepositoryModule_ProvideChallengeRepositoryFactory implements Factory<IChallengeRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public RepositoryModule_ProvideChallengeRepositoryFactory(
      Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public IChallengeRepository get() {
    return provideChallengeRepository(firestoreProvider.get());
  }

  public static RepositoryModule_ProvideChallengeRepositoryFactory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new RepositoryModule_ProvideChallengeRepositoryFactory(firestoreProvider);
  }

  public static IChallengeRepository provideChallengeRepository(FirebaseFirestore firestore) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideChallengeRepository(firestore));
  }
}
