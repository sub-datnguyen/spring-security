using NHibernateCore;
using NHibernateExercises1.Entities;
using System.Collections.Generic;

namespace NHibernateExercises1.Repositories
{
    public interface IProjectRepository : IBaseRepository<ProjectEntity>
    {
        IList<ProjectEntity> LoadProjectByNumbers(List<int> numbers);
        int GetMaxNumber();
        IList<int> GetAllProjectNumbers();
    }
}
