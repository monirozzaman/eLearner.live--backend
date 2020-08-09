from rest_framework import serializers
from .models import Courses,Learnerns,courseReviews

class LearnerSerializer(serializers.ModelSerializer):
    class Meta:
        model = Learnerns
        fields = '__all__'


class CoureReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = courseReviews
        fields = '__all__'



class CourseSerializer(serializers.ModelSerializer):
    learners = LearnerSerializer(many=True)
    courseReviews = CoureReviewSerializer(many=True)

    class Meta:
        model = Courses
        fields = '__all__'

    
    def create(self,validated_data):
        learner_validated_data = validated_data.pop('learners')
        courseReview_validated_data = validated_data.pop('courseReviews')

        course = Courses.objects.create(**validated_data)

        #learner_set_serializer = self.fields['learners']
        #courseReview_set_serializer = self.fields['courseReviews']

        for each in learner_validated_data:
            #each['courses'] = course
            Learnerns.objects.create(course=course,**each)


        for each in courseReview_validated_data:
            #each['courses'] = course
            courseReviews.objects.create(course=course,**each)


        #learners = learner_set_serializer.create(learner_validated_data)
        #courReviews = courseReview_set_serializer.create(courseReview_validated_data)
        return course