package com.pehom.theshi.domain.usecase

data class UseCases(
    val getAllVocabularyTitles: GetAllVocabularyTitles,
    val setTaskByTitle: SetTaskByTitle,
    val signInUseCase: SignInUseCase,
    val createFirestoreAccountUseCase: CreateFirestoreAccountUseCase,
    val readFirestoreUserInfoUseCase: ReadFirestoreUserInfoUseCase,
    val updateUserTasksFsUseCase: UpdateUserTasksFsUseCase
)
