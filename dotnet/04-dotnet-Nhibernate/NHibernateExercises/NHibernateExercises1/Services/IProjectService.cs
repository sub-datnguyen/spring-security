using System.Collections.Generic;

namespace NHibernateExercises1.Services
{
    public interface IProjectService
    {
        void ProcessProject(int number);
        void ProcessProjectInParallel(List<int> numbers);
        IList<int> GetAllProjectNumbers();
    }
}
