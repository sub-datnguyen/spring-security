using NHibernate.Mapping.ByCode;
using NHibernateCore;
using NHibernateExercises.Mappings;

namespace NHibernateExercises.Infrastructure
{
    public class ExerciseSessionFactory : SessionFactory
    {
        public ExerciseSessionFactory(string connection) : base(connection)
        {
        }

        public override void MappingTable(ModelMapper mapper)
        {
            mapper.AddMapping<ProjectMap>();
        }
    }
}
