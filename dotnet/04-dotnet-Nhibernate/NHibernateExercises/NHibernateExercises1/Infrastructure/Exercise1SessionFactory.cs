using NHibernate.Mapping.ByCode;
using NHibernateCore;
using NHibernateExercises1.Mappings;

namespace NHibernateExercises1.Infrastructure
{
    public class Exercise1SessionFactory : SessionFactory
    {
        public Exercise1SessionFactory(string connection) : base(connection)
        {
        }

        public override void MappingTable(ModelMapper mapper)
        {
            mapper.AddMapping<ProjectMap>();
        }
    }
}
