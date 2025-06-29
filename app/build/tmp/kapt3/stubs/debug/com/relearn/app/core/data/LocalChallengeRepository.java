package com.relearn.app.core.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/relearn/app/core/data/LocalChallengeRepository;", "Lcom/relearn/app/feature/challenges/domain/repository/IChallengeRepository;", "()V", "addChallenge", "", "challenge", "Lcom/relearn/app/feature/challenges/domain/model/Challenge;", "(Lcom/relearn/app/feature/challenges/domain/model/Challenge;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChallenge", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChallenges", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChallengesByStatus", "status", "Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;", "(Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChallenge", "updateChallengeStatus", "(Ljava/lang/String;Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LocalChallengeRepository implements com.relearn.app.feature.challenges.domain.repository.IChallengeRepository {
    
    public LocalChallengeRepository() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getChallenges(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.relearn.app.feature.challenges.domain.model.Challenge>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getChallengesByStatus(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.ChallengeStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.relearn.app.feature.challenges.domain.model.Challenge>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addChallenge(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.Challenge challenge, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateChallenge(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.Challenge challenge, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteChallenge(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateChallengeStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.ChallengeStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}