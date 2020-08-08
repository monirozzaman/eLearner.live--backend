from django.db import models
from django.core.validators import MinValueValidator, MaxValueValidator
# Create your models here.

class Courses(models.Model):
    courseName = models.CharField(max_length=30,blank=False)
    courseGoal = models.CharField(max_length=30,blank=False)
    courseMaxNumberOfLearner = models.IntegerField(blank=False)
    courseOriantationDate = models.DateField(auto_now=False, auto_now_add=False)
    courseStartingDate = models.DateField(auto_now=False, auto_now_add=False)
    courseFinishingDate = models.DateField(auto_now=False, auto_now_add=False)
    courseTotalDuiration = models.CharField(max_length=30,blank=False)
    courseNumberOfClasses = models.CharField(max_length=30,blank=False)
    courseClassDuiration = models.CharField(max_length=30,blank=False)
    courseClassStartTime = models.TimeField(auto_now=False, auto_now_add=False)
    courseClassEndTime = models.TimeField(auto_now=False, auto_now_add=False)
    courseInstractorId = models.CharField(max_length=50)
    courseInstractorName = models.CharField(max_length=30)
    courseInstractorQualification = models.CharField(max_length=30)
    courseInstractorPhoneNumber = models.CharField(max_length=30)
    coursePriceInTk = models.CharField(max_length=30)



class Learnerns(models.Model):
    courses = models.ForeignKey(Courses,on_delete=models.CASCADE)
    learnerId = models.CharField(max_length=50)
    isPaymentValified = models.BooleanField()


class courseReviews(models.Model):
    Courses = models.ForeignKey(Courses,on_delete=models.CASCADE)
    learnerId = models.CharField(max_length=50)
    star = models.IntegerField(validators=[MinValueValidator(0),MaxValueValidator(5)])