using NHibernate;
using NHibernate.Cfg;
using NHibernate.Cfg.Loquacious;
using NHibernate.Dialect;
using NHibernate.Driver;
using NHibernate.Mapping.ByCode;
using System;

namespace NHibernateCore
{
    public class SessionFactory
    {
        protected virtual string DefaultSchema => String.Empty;
        protected readonly string _connection;

        public SessionFactory(string connection)
        {
            _connection = connection;
        }

        public ISessionFactory GetSessionFactory()
        {
            var config = new Configuration();
            config.DataBaseIntegration(DataBaseIntegrationConfiguration);
            config.SetProperty(global::NHibernate.Cfg.Environment.WrapResultSets, Boolean.TrueString);
            config.SetProperty(global::NHibernate.Cfg.Environment.GenerateStatistics, Boolean.FalseString);
            config.SetProperty(global::NHibernate.Cfg.Environment.QueryStartupChecking, Boolean.TrueString);
            config.SetProperty(global::NHibernate.Cfg.Environment.PrepareSql, Boolean.TrueString);
            config.SetProperty(global::NHibernate.Cfg.Environment.DefaultSchema, DefaultSchema);

            var mapper = new ModelMapper();
            MappingTable(mapper);
            var mappings = mapper.CompileMappingForAllExplicitlyAddedEntities();
            config.AddDeserializedMapping(mappings, null);

            AdditionalConfiguration(config);

            return config.BuildSessionFactory();
        }

        protected void DataBaseIntegrationConfiguration(IDbIntegrationConfigurationProperties db)
#pragma warning restore CS0618 // Type or member is obsolete
        {
            db.Dialect<MsSql2012Dialect>();
            db.Driver<Sql2008ClientDriver>();
            db.HqlToSqlSubstitutions = "true=1;false=0";
            db.LogSqlInConsole = false;
            db.LogFormattedSql = true;
            db.ConnectionString = _connection;
            db.KeywordsAutoImport = Hbm2DDLKeyWords.None;
            db.Timeout = 150;
        }

        /// <summary>
        /// Can be overridden to add additional filters, interceptors, ...
        /// </summary>
        protected virtual void AdditionalConfiguration(Configuration config) { }

        public virtual void MappingTable(ModelMapper mapper)
        {
            // TODO
        }
    }
}
