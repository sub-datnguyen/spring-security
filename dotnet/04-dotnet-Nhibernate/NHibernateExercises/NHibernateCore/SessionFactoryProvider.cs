using log4net;
using NHibernate;
using Ninject;
using Ninject.Activation;

namespace NHibernateExercises
{
    public class SessionFactoryProvider<T> : Provider<ISessionFactory> where T : SessionFactory, new()
    {
        [Inject]
        public ILog Logger { get; set; }

        protected override ISessionFactory CreateInstance(IContext context)
        {
            var sessionFactory = new T();
            sessionFactory.Logger = Logger;
            return sessionFactory.GetSessionFactory();
        }
    }
}
