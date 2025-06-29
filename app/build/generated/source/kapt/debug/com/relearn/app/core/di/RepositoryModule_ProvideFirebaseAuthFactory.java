package com.relearn.app.core.di;

import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RepositoryModule_ProvideFirebaseAuthFactory implements Factory<FirebaseAuth> {
  @Override
  public FirebaseAuth get() {
    return provideFirebaseAuth();
  }

  public static RepositoryModule_ProvideFirebaseAuthFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAuth provideFirebaseAuth() {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideFirebaseAuth());
  }

  private static final class InstanceHolder {
    private static final RepositoryModule_ProvideFirebaseAuthFactory INSTANCE = new RepositoryModule_ProvideFirebaseAuthFactory();
  }
}
