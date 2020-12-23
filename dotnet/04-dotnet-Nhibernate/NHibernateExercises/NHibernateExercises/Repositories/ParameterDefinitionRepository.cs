using NHibernateCore;
using NHibernateExercises.Entities;

namespace NHibernateExercises.Repositories
{
    public class ParameterDefinitionRepository : BaseRepository<ParameterDefinitionEntity>, IParameterDefinitionRepository
    {
    }
}
