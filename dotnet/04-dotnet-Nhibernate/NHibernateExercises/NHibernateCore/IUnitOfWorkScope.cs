using NHibernate;
using System;

namespace NHibernateCore
{
    public interface IUnitOfWorkScope : IDisposable
    {

        ISession Session
        {
            get;
        }

        void Complete();

        /// <summary>
        /// Initialize a lazy many-to-one or one-to-may/many-to-many (i.e. a colleciton)
        /// </summary>
        /// <param name="proxy"></param>
        void InitializeProxy(object proxy);
    }
}
