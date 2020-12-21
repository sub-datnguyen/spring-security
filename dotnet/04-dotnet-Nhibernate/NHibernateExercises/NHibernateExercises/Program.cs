using NHibernate;
using NHibernateCore;
using NHibernateExercises.Infrastructure;
using NHibernateExercises.Repositories;
using NHibernateExercises.Services;
using SimpleInjector;
using System;
using System.Configuration;
using System.Linq;

namespace NHibernateExercises
{
    class Program
    {
        static void Main(string[] args)
        {
            string connectionString = ConfigurationManager.ConnectionStrings["Project"].ConnectionString;
            var container = new Container();
            container.RegisterInstance(new ExerciseSessionFactory(connectionString).GetSessionFactory());
            container.Register<IUnitOfWorkProvider, UnitOfWorkProvider>(Lifestyle.Singleton);
            container.Register< IInterceptor, NullInterceptor>(Lifestyle.Singleton);
            container.Register<IProjectRepository, ProjectRepository>(Lifestyle.Singleton);
            container.Register<IProjectService, ProjectService>(Lifestyle.Singleton);

            var projectService = container.GetInstance<IProjectService>();
            var numbers = projectService.GetAllProjectNumbers().Distinct().ToList();

            Console.WriteLine("Exercise 1: why the transaction deadlock occurs below with ProcessProjectInParallel ? How will we solve it ?");
            // Question : why the transaction deadlock occurs ? How will we solve it ?
            projectService.ProcessProjectInParallel(numbers);
            Console.WriteLine("End Exercise 1");
        }
    }
}
