package co.nz.tsb.interview.bankrecmatchmaker.di

import co.nz.tsb.interview.bankrecmatchmaker.data.AccountingRecordRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.DefaultAccountingRecordRepository
import co.nz.tsb.interview.bankrecmatchmaker.data.source.AccountingRecordDataSource
import co.nz.tsb.interview.bankrecmatchmaker.data.source.DefaultAccountingRecordDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAccountingRecordRepository(repository: DefaultAccountingRecordRepository): AccountingRecordRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindAccountingRecordDataSource(dataSource: DefaultAccountingRecordDataSource): AccountingRecordDataSource
}
