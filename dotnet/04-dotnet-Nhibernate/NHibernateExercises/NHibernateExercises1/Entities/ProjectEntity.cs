using NHibernateCore;
using System;

namespace NHibernateExercises.Entities
{
    public class ProjectEntity : BaseEntity
    {
        public virtual int Number { get; set; }
        public virtual string Name { get; set; }
        public virtual DateTime StartDate { get; set; }
        public virtual DateTime? EndDate { get; set; }
    }
}
