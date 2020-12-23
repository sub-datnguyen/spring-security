using NHibernate;
using NHibernateCore;
using NHibernateExercises.Entities;
using NHibernateExercises.Infrastructure;
using NHibernateExercises.Repositories;
using NHibernateExercises.Services;
using SimpleInjector;
using System;
using System.Configuration;
using System.Diagnostics;
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
            container.Register<IInterceptor, NullInterceptor>(Lifestyle.Singleton);
            container.Register<IProjectRepository, ProjectRepository>(Lifestyle.Singleton);
            container.Register<IProjectService, ProjectService>(Lifestyle.Singleton);
            container.Register<IParameterDefinitionRepository, ParameterDefinitionRepository>(Lifestyle.Singleton);
            container.Register<IParameterService, ParameterService>(Lifestyle.Singleton);

            var projectService = container.GetInstance<IProjectService>();
            var parameterService = container.GetInstance<IParameterService>();
            var numbers = projectService.GetAllProjectNumbers().Distinct().ToList();

            // Exercise 1: why the transaction deadlock occurs below with ProcessProjectInParallel ? How will we solve it ?
            Console.WriteLine("Exercise 1: why the transaction deadlock occurs below with ProcessProjectInParallel ? How will we solve it ?");
            projectService.ProcessProjectInParallel(numbers);
            Console.WriteLine("End Exercise 1");

            // Exercise 2: Please improve the performance for two methods CreateAllParameters & DeleteAllParameters 
            Console.WriteLine("Exercise 2");
            Stopwatch stopWatch = new Stopwatch();
            stopWatch.Start();
            parameterService.CreateAllParameters();
            stopWatch.Stop();
            Console.WriteLine($"Create all parameters in {stopWatch.Elapsed.TotalSeconds} seconds");
            stopWatch.Start();
            parameterService.DeleteAllParameters();
            stopWatch.Stop();
            Console.WriteLine($"Delete all parameters in {stopWatch.Elapsed.TotalSeconds} seconds");

            // Exercise 3 : why OptimisticVersionException is thrown while there is no row into DB for table ParameterDefinition + ParameterValue ?
            try
            {
                parameterService.DeleteAllParameters();
                var parameter = new ParameterDefinitionEntity
                {
                    Id = 1,
                    Name = $"Parameter1",
                    Type = "Integer",
                    RowVersion = 1
                };
                var parameterValue = new ParameterValueEntity
                {
                    Id = 1,
                    Value = "1",
                    Year = DateTime.Now.Year,
                    ParameterDefinition = parameter,
                    RowVersion = 1
                };
                parameter.ParameterValues.Add(parameterValue);
                parameterService.InsertParameter(parameter);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error to process for number : " + ex);
            }
        }
    }
}
