using NHibernateCore;
using NHibernateExercises.Entities;
using NHibernateExercises.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NHibernateExercises.Services
{
    public class ProjectService : IProjectService
    {
        private const string Date_Format = "dd.MM.yyyy";
        private const string Date_Time_Format = "yyyy_MM_dd_HH_ss";
        private readonly IProjectRepository _projectRepository;
        private readonly IUnitOfWorkProvider _unitOfWorkProvider;

        public ProjectService(IProjectRepository projectRepository, IUnitOfWorkProvider unitOfWorkProvider)
        {
            _projectRepository = projectRepository;
            _unitOfWorkProvider = unitOfWorkProvider;
        }

        public void ProcessProject(int number)
        {
            try
            {
                Process(number);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error to process for number : " + number + ". Reason : " + ex.InnerException?.Message);
            }
        }

        private void Process(int number)
        {
            using var uow = _unitOfWorkProvider.Provide();
            InsertProject(number);
            ExportProjects(number);
            uow.Complete();
        }

        private void ExportProjects(int number)
        {
            WriteToFile(number, LoadProjectByNumbers(number));
        }

        private IList<ProjectEntity> LoadProjectByNumbers(int number)
        {
            using var uow = _unitOfWorkProvider.Provide();
            var projects = _projectRepository.LoadProjectByNumbers(new List<int>() { number });
            uow.Complete();
            return projects;
        }

        private static void WriteToFile(int number, IList<ProjectEntity> projects)
        {
            if (projects.Any())
            {
                var context = new StringBuilder();
                foreach (var project in projects)
                {
                    context.AppendLine($"{project.Id},{project.Number},{project.Name},{project.StartDate.ToString(Date_Format)},{project.EndDate?.ToString(Date_Format)}");
                }
                System.IO.File.WriteAllText(@$"{number}_{DateTime.Now.ToString(Date_Time_Format)}.txt", context.ToString());
            }
        }

        private void InsertProject(int number)
        {
            using var uow = _unitOfWorkProvider.Provide();
            _projectRepository.SaveOrUpdate(new Entities.ProjectEntity
            {
                Number = number,
                Name = "ABC",
                StartDate = DateTime.Now,
                RowVersion = 1
            });
            uow.Complete();
        }

        public void ProcessProjectInParallel(List<int> numbers)
        {
            // We use parallel process to improve the performance
            // please don't change anything
            Parallel.ForEach(
                        numbers,
                        new ParallelOptions { MaxDegreeOfParallelism = 2 },
                        number => ProcessProject(number));
        }

        public IList<int> GetAllProjectNumbers()
        {
            using var uow = _unitOfWorkProvider.Provide();
            var result = _projectRepository.GetAllProjectNumbers();
            uow.Complete();
            return result;
        }
    }
}
