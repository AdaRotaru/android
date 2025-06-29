package com.relearn.app.feature.challenges.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a6@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00a6@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0013\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/relearn/app/feature/challenges/domain/repository/IChallengeRepository;", "", "addChallenge", "", "challenge", "Lcom/relearn/app/feature/challenges/domain/model/Challenge;", "(Lcom/relearn/app/feature/challenges/domain/model/Challenge;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChallenge", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChallenges", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getChallengesByStatus", "status", "Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;", "(Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChallenge", "updateChallengeStatus", "(Ljava/lang/String;Lcom/relearn/app/feature/challenges/domain/model/ChallengeStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface IChallengeRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChallenges(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.relearn.app.feature.challenges.domain.model.Challenge>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChallengesByStatus(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.ChallengeStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.relearn.app.feature.challenges.domain.model.Challenge>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addChallenge(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.Challenge challenge, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateChallenge(@org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.Challenge challenge, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteChallenge(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateChallengeStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.relearn.app.feature.challenges.domain.model.ChallengeStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}