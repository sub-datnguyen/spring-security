using NHibernate;
using NHibernateCore;
using NHibernateExercises1.Infrastructure;
using NHibernateExercises1.Repositories;
using NHibernateExercises1.Services;
using SimpleInjector;
using System;
using System.Configuration;
using System.Linq;

namespace NHibernateExercises1
{
    class Program
    {
        static void Main(string[] args)
        {
            string connectionString = ConfigurationManager.ConnectionStrings["Project"].ConnectionString;
            var container = new Container();
            container.RegisterInstance(new Exercise1SessionFactory(connectionString).GetSessionFactory());
            container.Register<IUnitOfWorkProvider, UnitOfWorkProvider>(Lifestyle.Singleton);
            container.Register< IInterceptor, NullInterceptor>(Lifestyle.Singleton);
            container.Register<IProjectRepository, ProjectRepository>(Lifestyle.Singleton);
            container.Register<IProjectService, ProjectService>(Lifestyle.Singleton);

            var projectService = container.GetInstance<IProjectService>();
            var numbers = projectService.GetAllProjectNumbers().Distinct().ToList();

            Console.WriteLine("Start ProcessProjectInParallel");
            // Question : why the transaction deadlock occurs ? How will we solve it ?
            projectService.ProcessProjectInParallel(numbers);
            Console.WriteLine("End ProcessProjectInParallel");
        }
    }
}
