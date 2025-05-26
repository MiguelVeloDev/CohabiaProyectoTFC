package com.example.cohabiaproject.di

import com.example.cohabiaproject.data.source.remote.CasaFirestoreRepository
import com.example.cohabiaproject.data.source.remote.ElectrodomesticoFirestoreRepository
import com.example.cohabiaproject.data.source.remote.EventoFirestoreRepository
import com.example.cohabiaproject.data.source.remote.FinanzaFireStoreRepository
import com.example.cohabiaproject.data.source.remote.NotaFirestoreRepository
import com.example.cohabiaproject.data.source.remote.ProductoFirestoreRepository
import com.example.cohabiaproject.data.source.remote.UsoProgramaFirestoreRepository
import com.example.cohabiaproject.data.source.remote.UsuarioFirestoreRepository
import com.example.cohabiaproject.domain.repository.CasaRepository
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.EventoUseCases.DeleteEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.GetEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.SaveEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.UpdateEventoUseCase
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import com.example.cohabiaproject.domain.repository.NotaRepository
import com.example.cohabiaproject.domain.repository.ProductoRepository
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.UsuarioRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.DeleteElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoByIdUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.DeleteFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetFinanzaEsteMesUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetTodasDeudasUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetTodasFinanzasEsteMesUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.SaveFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.UpdateFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.DeleteNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.GetNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.SaveNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.UpdateNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.DeleteProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.GetProductoByIdUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.GetProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.SaveProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.UpdateProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.SaveCasaUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.DeleteUsuarioUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.GetUsuarioByIdUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.GetUsuarioUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.SaveUsuarioUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.UpdateUsuarioUseCase
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.LoginViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.NotaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single<ElectrodomesticoRepository> { ElectrodomesticoFirestoreRepository(get()) }
    single<UsoProgramaRepository> { UsoProgramaFirestoreRepository(get()) }
    single<NotaRepository> { NotaFirestoreRepository(get()) }
    single<FinanzaRepository> { FinanzaFireStoreRepository(get()) }
    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }

    single<CasaRepository> { CasaFirestoreRepository(get()) }
    single<UsuarioRepository> { UsuarioFirestoreRepository(get()) }
    single<EventoRepository> { EventoFirestoreRepository(get()) }
    single<ProductoRepository> { ProductoFirestoreRepository(get()) }

    factory { GetUsuarioUseCase(get()) }
    factory { SaveUsuarioUseCase(get()) }
    factory { UpdateUsuarioUseCase(get()) }
    factory { DeleteUsuarioUseCase(get()) }
    factory { GetUsuarioByIdUseCase(get()) }

    factory { GetProductoUseCase(get())}
    factory { SaveProductoUseCase(get())}
    factory { UpdateProductoUseCase(get())}
    factory { DeleteProductoUseCase(get())}
    factory { GetProductoByIdUseCase(get())}

    factory { SaveCasaUseCase(get()) }
    viewModel { CasaViewModel(get(), get()) }

    factory { GetElectrodomesticoUseCase(get()) }
    factory { SaveElectrodomesticoUseCase(get()) }
    factory { UpdateElectrodomesticoUseCase(get()) }
    factory { GetElectrodomesticoByIdUseCase(get()) }
    factory { DeleteElectrodomesticoUseCase(get()) }

    factory { GetNotaUseCase(get()) }
    factory { SaveNotaUseCase(get()) }
    factory { UpdateNotaUseCase(get()) }
    factory { DeleteNotaUseCase(get()) }

    factory { SaveEventoUseCase(get()) }
    factory { GetEventoUseCase(get()) }
    factory { UpdateEventoUseCase(get()) }
    factory { DeleteEventoUseCase(get()) }

    factory { GetFinanzaUseCase(get()) }
    factory { SaveFinanzaUseCase(get()) }
    factory { UpdateFinanzaUseCase(get()) }
    factory { DeleteFinanzaUseCase(get()) }
    factory { GetFinanzaEsteMesUseCase(get()) }
    factory { GetTodasFinanzasEsteMesUseCase(get()) }
    factory { GetTodasDeudasUseCase(get()) }

    viewModel { ElectrodomesticoViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { NotaViewModel(get(), get(), get(), get()) }
    viewModel { FinanzasViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel {UsuarioViewModel(get(), get(), get(), get(), get())}
    viewModel {EventoViewModel(get(), get(), get(), get())}
    viewModel {ProductoViewModel(get(), get(), get(), get())}


}
