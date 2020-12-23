using System.Collections.Generic;

namespace NHibernateExercises.Services
{
    public interface IProjectService
    {
        void ProcessProject(int number);
        void ProcessProjectInParallel(List<int> numbers);
        IList<int> GetAllProjectNumbers();
        void ImportProjects();
    }
}
